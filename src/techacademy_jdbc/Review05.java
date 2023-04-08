package techacademy_jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {

        // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {



            // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "20011004"
                    );
            // 4. DBとやりとりする窓口（Statementオブジェクト）の作成
            String sql = "SELECT * FROM person  where ID = ?";
            pstmt = con.prepareStatement(sql);

            System.out.print("検索キーワードを入力してください > ");
            String str1 = keyIn();

            // 5, 6. Select文の実行と結果を格納／代入
            pstmt.setString(1, str1);
            rs = pstmt.executeQuery();
            // 7. 結果を表示する
            while( rs.next() ){
                // Name列の値を取得
                String name = rs.getString("name");
                int age = rs.getInt("age");
                // 取得した値を表示
                System.out.println(name);
                System.out.print(age);
            }
        } catch (ClassNotFoundException e) {
            //エラー発生時の表示〈lassNotFoundExceptionが発生したとき）
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        }
        catch (SQLException e) {
            //エラー発生時の表示（データベースに接続できない、見つからない異常が発生したとき）
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally { //データベースとの切断処理は途中で例外が発生しても、しなくても、実行できるよう
            // 8. 接続を閉じる
            if( rs != null ){
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if( pstmt != null ){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.err.println("Statementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if( con != null ){
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }

            }

        }

    }
    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {

        }
        return line;
    }


}

