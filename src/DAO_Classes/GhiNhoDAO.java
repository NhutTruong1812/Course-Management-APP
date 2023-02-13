package DAO_Classes;

import Tool_Classes.JDBCHelper;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GhiNhoDAO implements Serializable{

    public List<String> selectBySQL(String sql, Object... thamSo) {
        // List tạm
        List<String> list = new ArrayList<>();

        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL(sql, thamSo);

                // Trả kết quả
                while (ketQua.next()) {
                    // Đối tượng chuyên đề
                    String taiKhoan = ketQua.getString(1);
                    String matKhau = ketQua.getString(2);

                    // Thêm vào list tạm
                    list.add(taiKhoan);
                    list.add(matKhau);
                }
            } finally {
                ketQua.getStatement().getConnection().close();
                JDBCHelper.closeConnectSQL();
            }
        } catch (Exception e) {
            System.out.println("loi: " + e);
        }
        return list;
    }

    public void insert(String taiKhoan, String matKhau) {
        String sql = "INSERT INTO GHINHO VALUES (?,?)";
        JDBCHelper.updateSQL(sql, taiKhoan, matKhau);
    }

    public void update(String taiKhoan, String matKhau) {
        String sql = "update ghinho set matkhau = ?, manhanvien = ?";
        JDBCHelper.updateSQL(sql, matKhau, taiKhoan);
    }
    
    public void delete(String taiKhoan) {
        String sql = "delete ghinho where manhanvien = ?";
        JDBCHelper.updateSQL(sql, taiKhoan);
    }

}
