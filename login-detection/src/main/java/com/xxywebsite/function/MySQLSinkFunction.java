package com.xxywebsite.function;

import com.xxywebsite.model.WarningInfo;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class MySQLSinkFunction extends RichSinkFunction<WarningInfo> {
    private Connection connection;
    @Override
    public void open(Configuration parameters) throws SQLException, ClassNotFoundException {
//        URL resource = MySQLSinkFunction.class.getResource("/settings.yml");
//        Yaml yaml = new Yaml();
//        Map map = yaml.loadAs(new FileInputStream(resource.getPath()), Map.class);


        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://mymysql:3306/note?serverTimezone=Asia/Shanghai","root","123456");
    }

    @Override
    public void close() throws Exception {
//        super.close();
        connection.close();
    }

    @Override
    public void invoke(WarningInfo value, Context context) throws Exception {
        String sql = "REPLACE INTO login_warning(user_id, count, begin_time, end_time) values(?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, value.getUserId());
        preparedStatement.setLong(2, value.getCount());
        preparedStatement.setObject(3, value.getBeginTime());
        preparedStatement.setObject(4, value.getEndTime());

        preparedStatement.execute();

    }
}
