package lk.ijse.plant.dao;

import lk.ijse.plant.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {// SQLUtil kiyana class ek hadala contoller wala ena dbconnection repeate code tikA ain krl ekkata dala thiynawa
    public static <T>T execute(String sql, Object... args) throws SQLException{// string sql kiyanne sql eke mokdd kiyana eka e kiynne select qry ekkd update ekkd kiyn eka... Object.... atgs kiyla vargs use karala thhiyenne integer.. string boolen e hama typ ekkinma variable alla gann ona nis
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++){
            pstm.setObject((i+1),args[i]);
        }
        if (sql.startsWith("SELECT") | sql.startsWith("select")) {
            return (T) pstm.executeQuery();
        }else {
            return (T) (Boolean) (pstm.executeUpdate() > 0);
        }
    }
}
