package com.example.coding_test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class CodingTestApplication {

    private static final Set<String> SYSTEM_DATABASES = new HashSet<>(Arrays.asList(
        "information_schema", "mysql", "performance_schema", "sys"));

    private static final String dbDumFilePath = "/home/mysql/backup"; // 경로 원하는 곳으로 변경!!

    /**
     * 실행 방법 -> java 클래스명.clss {dbPort}, {dbUser}
     * 1. DB 연결
     * 2. show databases; 로 데이터베이스 목록 조회 (시스템 데이터베이스는 제외)
     * 3. database dump 파일 생성 (경로에 이미 존재한다면 삭제 후 생성)
     * 4. 데이터베이스 캐릭터셋 변경
     * 5. 테이블 단위 엔진 + 캐릭터셋 변경 (Alter 명령문 1개)
     *
     * 우려되는 부분
     * 1. 해당 작업하는 동안 실제 기관에서 DB 사용 가능 유무
     * 2.
     */
    public static void main(String[] args) {

        if (args.length < 3) {
            System.err.println("❌ 사용법: java MySQLRunner <port> <user>");
            return;
        }

        String port = args[0];
        String user = args[1];
        String password = "";

        String url = "jdbc:mysql://localhost:" + port;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            System.out.println("✅ 연결 성공: " + url);

            Statement stmt = conn.createStatement();

            // show databases
            var rs = stmt.executeQuery("SHOW DATABASES");

            System.out.println("📂 데이터베이스 목록:");

            // 데이터베이스 목록 반복문 실행
            while (rs.next()) {

                String dbName = rs.getString(1);

                // 시스템 데이터베이스 제외
                if (SYSTEM_DATABASES.contains(dbName)) {
                    continue;
                }

                // 덤프 파일명 + 덤프 파일 최종 경로 설정
                String dumpFileName = dbName + ".sql";
                String dbDumpFileFullPath = dbDumFilePath + "/" + dumpFileName;
                File dumpFile = new File(dbDumpFileFullPath);

                // 기존 덤프 파일이 있다면 삭제
                if (dumpFile.exists()) {
                    if (dumpFile.delete()) {
                        System.out.println("🗑️ 기존 덤프 파일 삭제됨: " + dbDumpFileFullPath);
                    } else {
                        System.err.println("❌ 기존 덤프 파일 삭제 실패");
                        return;
                    }
                }

                // mysqldump 명령어
                String dumpCommand = String.format(
                    "mysqldump -u%s -p%s --databases %s --single-transaction --routines --triggers > %s",
                    user, password, dbName, dbDumpFileFullPath);

                // Windows 명령어 실행
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", dumpCommand);
                Process process = pb.start();

                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                while ((line = errorReader.readLine()) != null) {
                    System.err.println(line);
                }

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("✅ 백업 완료: " + dbDumpFileFullPath);
                } else {
                    System.err.println("❌ 백업 실패 (exit code: " + exitCode + ")");
                    return;
                }

                String alterDbSql = "ALTER DATABASE `" + dbName + "` CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci";

                try (Statement alterDbStmt = conn.createStatement()) {

                    alterDbStmt.execute(alterDbSql);
                    System.out.println("✅ DB Charset Updated: " + dbName);

                } catch (Exception e) {

                    System.err.println("❌ DB Charset Update Failed: " + dbName + " - " + e.getMessage());

                    throw new RuntimeException(e); // DB 명령어 오류시 반복문을 종료하고 로그 확인 !!
                }

                // 2. 데이터베이스 별 테이블 목록을 반복하면서 Alter 엔진 + 캐릭터셋 명령어 수행 (중간에 오류 발생시 어느 테이블에서 오류가 발생했는지 파악하기 위해 하나씩 Alter문 수행)

                String tableQuery = "SELECT table_name, engine FROM information_schema.tables WHERE table_schema = ? AND table_type = 'BASE TABLE'";

                try (PreparedStatement ps = conn.prepareStatement(tableQuery)) {

                    ps.setString(1, dbName);

                    try (ResultSet trs = ps.executeQuery()) {

                        while (trs.next()) {

                            String tableName = trs.getString("table_name");
                            String currentEngine = trs.getString("engine");

                            // 변경할 엔진 설정 (예: 모두 InnoDB로)
                            String targetEngine = "InnoDB";

                            // 이미 InnoDB로 변경된 상태라면 패스!!
                            if (targetEngine.equals(currentEngine)) {
                                continue;
                            }

                            // 엔진 + 캐릭터셋 변경 명령어
                            String alterTableSql = String.format(
                                "ALTER TABLE `%s`.`%s` ENGINE = %s, CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci",
                                dbName, tableName, targetEngine);

                            try (Statement alterStmt = conn.createStatement()) {

                                alterStmt.execute(alterTableSql);
                                System.out.println("  ✅ Table Updated: " + tableName);

                            } catch (Exception e) {

                                System.err.println("  ❌ Table Update Failed: " + tableName + " - " + e.getMessage());

                                throw new RuntimeException(e);
                            }
                        }
                    }
                }

                System.out.println("- " + rs.getString(1));
            }

        } catch (Exception e) {

            System.err.println("❌ 전체 프로세스 실패: " + e.getMessage());

            throw new RuntimeException(e); // 프로세스 종료 (이후 코드 실행 안됨)
        }

    }

}
