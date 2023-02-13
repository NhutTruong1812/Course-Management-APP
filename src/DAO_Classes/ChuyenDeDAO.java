package DAO_Classes;

import Entity_Classes.ChuyenDe;
import Tool_Classes.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Topic: Interface tổ chức chuẩn đặt tên phương thức cho các lớp DAO
 * Content: Phần mềm quản lý hệ thống đào tạo - WolvesEdu...
            * ...
 * Version: 1.0.0
 * Author: Four Wolves >> Văn Hữu Đức - PC01395 >> Trần Thanh Hồ - PC02096 >> Huỳnh Nhật Quang - PC01597 >> Nguyễn Văn Nhựt Trường - PC01752
 * Date: 17th September 2021 >> .. .. 2021
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

public class ChuyenDeDAO implements WolvesEduDAO<ChuyenDe, String>{

    @Override
    public List<ChuyenDe> selectAll() {
        // List tạm
        List<ChuyenDe> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from chuyende");

        return list;
    }

    @Override
    public ChuyenDe selectById(String ma) {
        // List tạm
        List<ChuyenDe> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from chuyende where maChuyenDe = ?", ma);

        return list.get(0);
    }

    @Override
    public List<ChuyenDe> selectBySQL(String sql, Object... thamSo) {
        // List tạm
        List<ChuyenDe> list = new ArrayList<>();

        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL(sql, thamSo);

                // Trả kết quả
                while (ketQua.next()) {
                    // Đối tượng chuyên đề
                    ChuyenDe doiTuong = new ChuyenDe(ketQua.getString(1), ketQua.getString(2), (long) ketQua.getFloat(3), ketQua.getInt(4), ImageHelper.byteToImage(ketQua.getBytes(5)), ketQua.getString(6));

                    // Thêm vào list tạm
                    list.add(doiTuong);
                }
            } finally {
                ketQua.getStatement().getConnection().close();
                JDBCHelper.closeConnectSQL();
            }
        } catch (SQLException e) {
        }

        return list;
    }

    // 
    @Override
    public void insert(ChuyenDe doiTuongMoi, String linkAnh) {
        String sql = "INSERT INTO CHUYENDE(MACHUYENDE, TENCHUYENDE, HOCPHI, THOILUONG, ANH , MOTA) VALUES (?,?,?,?,?,?)";
        JDBCHelper.updateSQL(sql, doiTuongMoi.getMaChuyenDe(), doiTuongMoi.getTenChuyenDe(), doiTuongMoi.getHocPhi(), doiTuongMoi.getThoiLuong(), ImageHelper.imageToByte(linkAnh), doiTuongMoi.getMoTa());
    }

    @Override
    public void update(ChuyenDe doiTuongCapNhat, String ma, String linkAnh) {
        String sql = "UPDATE CHUYENDE SET MACHUYENDE = ?, TENCHUYENDE = ? , HOCPHI = ?, THOILUONG = ?, Anh = ?, MOTA = ? WHERE MACHUYENDE = ?";
        JDBCHelper.updateSQL(sql, doiTuongCapNhat.getMaChuyenDe(), doiTuongCapNhat.getTenChuyenDe(), doiTuongCapNhat.getHocPhi(), doiTuongCapNhat.getThoiLuong(), ImageHelper.imageToByte(linkAnh), doiTuongCapNhat.getMoTa(), ma);
    }

    @Override
    public void delete(String ma) {
        String sql = "delete from chuyende where machuyende = ?";
        JDBCHelper.updateSQL(sql, ma);
    }

    @Override
    public void insert(ChuyenDe doiTuongMoi) {
        String sql = "INSERT INTO CHUYENDE(MACHUYENDE, TENCHUYENDE, HOCPHI, THOILUONG, ANH , MOTA) VALUES (?,?,?,?,?,?)";
        JDBCHelper.updateSQL(sql, doiTuongMoi.getMaChuyenDe(), doiTuongMoi.getTenChuyenDe(), doiTuongMoi.getHocPhi(), doiTuongMoi.getThoiLuong(), ImageHelper.imageToByte(null), doiTuongMoi.getMoTa());
    }

    @Override
    public void update(ChuyenDe doiTuongCapNhat, String ma) {
        String sql = "UPDATE CHUYENDE SET MACHUYENDE = ?, TENCHUYENDE = ? , HOCPHI = ?, THOILUONG = ?, MOTA = ? WHERE MACHUYENDE = ?";
        JDBCHelper.updateSQL(sql, doiTuongCapNhat.getMaChuyenDe(), doiTuongCapNhat.getTenChuyenDe(), doiTuongCapNhat.getHocPhi(), doiTuongCapNhat.getThoiLuong(), doiTuongCapNhat.getMoTa(), ma);
    }
    //Tìm kiếm chuyên đề
    public List<ChuyenDe> selectAll(String ma) {
        // List tạm
        List<ChuyenDe> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from CHUYENDE where MaChuyenDe like N'%" + ma + "%' or TenChuyenDe like N'%" + ma + "%'");

        return list;
    }

    public void updateDemo(String maChuyenDe, String linkAnh) {
        String sql = "update chuyende set anh = ? where maChuyenDe = ?";
        JDBCHelper.updateSQL(sql, ImageHelper.imageToByte(linkAnh), maChuyenDe);
    }
    
}
