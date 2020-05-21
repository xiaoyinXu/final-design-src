package com.xxywebsite.function;

import com.xxywebsite.model.NoteStatistics;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLSinkFunction extends RichSinkFunction<NoteStatistics> {
    public static final String url = "jdbc:mysql://mymysql:3306/note?serverTimezone=Asia/Shanghai";
    public static final String username = "root";
    public static final String password = "123456";
    private Connection connection;

    @Override
    public void open(Configuration parameters) throws Exception {
//        super.open(parameters);
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);

    }

    @Override
    public void close() throws Exception {
//        super.close();
        connection.close();
    }

    @Override
    public void invoke(NoteStatistics value, Context context) throws Exception {
        // 先查询当前时间的结果是否存在表中
        // SELECT * FROM NOTE_STATISTIS WHERE UESR_ID =   BEGINTIME= END_TINE=
        String predict = "SELECT * FROM note_statistics WHERE user_id = ? AND begin_time = ? AND end_time = ?";
        PreparedStatement predictedSql = connection.prepareStatement(predict);
        predictedSql.setLong(1, value.getUserId());
        predictedSql.setObject(2, value.getBeginTime());
        predictedSql.setObject(3, value.getEndTime());
        ResultSet resultSet = predictedSql.executeQuery();
        String sql = null;
        if (resultSet.next()) { // 如果存在, 则覆盖写入, 即用update
            sql = "UPDATE note_statistics SET count = ? WHERE user_id = ? AND begin_time = ? AND end_time = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, value.getCount());

            preparedStatement.setLong(2, value.getUserId());
            preparedStatement.setObject(3, value.getBeginTime());
            preparedStatement.setObject(4, value.getEndTime());

            preparedStatement.execute();


        } else {
            sql = "INSERT INTO note_statistics(user_id, count, begin_time, end_time) VALUES(?, ?, ?, ?)";
            // 还是得REPLACE 幂等写入
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, value.getUserId());
            preparedStatement.setLong(2, value.getCount());
            preparedStatement.setObject(3, value.getBeginTime());
            preparedStatement.setObject(4, value.getEndTime());

            preparedStatement.execute();

        }




    }
}
