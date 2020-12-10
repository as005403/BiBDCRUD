package com.foxrider.dao;



import com.foxrider.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Component
@Scope("singleton")
public class GameDAO {
//    static String SORT = "SELECT * FROM game ORDER BY %s %s";
    @Autowired
    Connection connection;

    public GameDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Game> findAll() {
        final String SELECTALL = "SELECT * FROM game";

        List<Game> teamList= new LinkedList<>();
        try (PreparedStatement st = connection.prepareCall(SELECTALL);) {
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                Game tmp = new Game();
                tmp.setStadiumId(rs.getInt(1));
                tmp.setTeamName(rs.getString(2));
                tmp.setDate(rs.getString(3));
                tmp.setTime(rs.getString(4));
                tmp.setId(rs.getInt(5));

                teamList.add(tmp);
            }

            return teamList;
        } catch (SQLException e) {
            //some problem with connection or restrictions
            return null;

        }
    }

    public Game entityById(Integer key) {
        final String GETBYID = "SELECT * FROM game WHERE gameid=?";
        Game tmp = new Game();
        try (PreparedStatement st = connection.prepareCall(GETBYID);) {
            st.setInt(1, key);

            ResultSet rs = st.executeQuery();

            while (rs.next()){
                tmp.setStadiumId(rs.getInt(1));
                tmp.setTeamName(rs.getString(2));
                tmp.setDate(rs.getString(3));
                tmp.setTime(rs.getString(4));
                tmp.setId(rs.getInt(5));
            }

            if(tmp.getDate()==null)
                return null;

            return tmp;
        } catch (SQLException e) {
            //some problem with connection or restrictions
            return null;

        }
    }

    public boolean delete(Integer key) throws SQLException {
        final String DELETEKEY = "DELETE FROM game WHERE gameid=?";
        try (PreparedStatement st = connection.prepareCall(DELETEKEY);) {
            st.setInt(1, key);
            int num = st.executeUpdate();
            return num>0;
        } catch (SQLException e) {
            throw new SQLException("foreign key restriction");

        }
    }


    public boolean delete(Game entity) throws SQLException {
        return delete(entity.getId());
    }


    public boolean create(Game entity) throws SQLException {
        final String CREATE = "INSERT INTO game VALUES(?,?,?,?)";
        try (PreparedStatement st = connection.prepareCall(CREATE)) {
            st.setInt(1, entity.getStadiumId());
            st.setString(2, entity.getTeamName());
            st.setString(3, entity.getDate());
            st.setString(4, entity.getTime());
            st.execute();
            return true;
        } catch (SQLException e) {
            //some problem with connection or restriction primary key
            throw new SQLException("нарушение foreign ключей");

        }
    }

    public Game update(Game entity) throws SQLException {
        final String UPDATE = "UPDATE game SET stid=?, teamname=?, date=?,time=? where gameid=?";

        Game ret = entityById(entity.getId());
        if(ret==null)
            return null;

        try (PreparedStatement st = connection.prepareCall(UPDATE)) {
            st.setInt(1, entity.getStadiumId());
            st.setString(2, entity.getTeamName());
            st.setString(3, entity.getDate());
            st.setString(4, entity.getTime());
            st.setInt(5, entity.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            //some problem with connection or restriction primary key
            throw new SQLException("can't update due to some problems");

        }
        return ret;
    }


    public void close(){
        if(connection!=null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                System.out.println("Cannot close connection"+ connection+ "error");
            }
        }
    }

    @PreDestroy
    public void destroy(){
        close();
    }
}

