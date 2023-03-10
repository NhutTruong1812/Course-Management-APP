package DAO_Classes;

import Entity_Classes.KhoaHoc;
import Tool_Classes.DateHelper;
import Tool_Classes.JDBCHelper;
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

public class KhoaHocDAO implements WolvesEduDAO<KhoaHoc, String>{

    @Override
    public List<KhoaHoc> selectAll() {
        // List tạm
        List<KhoaHoc> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from KhoaHoc");

        return list;
    }

    @Override
    public KhoaHoc selectById(String ma) {
        // List tạm
        List<KhoaHoc> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from KhoaHoc where maKhoaHoc = ?", ma);

        return list.get(0);
    }

    @Override
    public List<KhoaHoc> selectBySQL(String sql, Object... thamSo) {
        // List tạm
        List<KhoaHoc> list = new ArrayList<>();

        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL(sql, thamSo);

                // Trả kết quả
                while (ketQua.next()) {
                    // Đối tượng chuyên đề
                    KhoaHoc doiTuong = new KhoaHoc(ketQua.getString(1), ketQua.getString(2), (long) ketQua.getFloat(3), ketQua.getInt(4), DateHelper.toString(ketQua.getDate(5), "dd-MM-yyyy"), ketQua.getString(6), DateHelper.toString(ketQua.getDate(7), "dd-MM-yyyy"), ketQua.getString(8));

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

    @Override
    public void insert(KhoaHoc doiTuongMoi) {
        String sql = "INSERT INTO KhoaHoc(MAKHOAHOC, MACHUYENDE, HOCPHI, THOILUONG, NGAYKHAIGIANG, NGUOITAO, NGAYTAO, GHICHU) VALUES (?,?,?,?,?,?,?,?)";
        JDBCHelper.updateSQL(sql, doiTuongMoi.getMaKhoaHoc(), doiTuongMoi.getMaChuyenDe(), doiTuongMoi.getHocPhi(), doiTuongMoi.getThoiLuong(), DateHelper.toDate(doiTuongMoi.getNgayKhaiGiang(), "dd-MM-yyyy"), doiTuongMoi.getNguoiTao(), DateHelper.toDate(doiTuongMoi.getNgayTao(), "dd-MM-yyyy"), doiTuongMoi.getGhiChu());
    }

    @Override
    public void update(KhoaHoc doiTuongCapNhat, String ma) {
        String sql = "UPDATE KHOAHOC SET MaChuyenDe = ?, Hocphi = ?, Thoiluong = ?, NgayKhaiGiang = ?, GhiChu = ?, NguoiTao = ?, NgayTao = ? WHERE MaKhoaHoc = ?";
        JDBCHelper.updateSQL(sql, doiTuongCapNhat.getMaChuyenDe(), doiTuongCapNhat.getHocPhi(), doiTuongCapNhat.getThoiLuong(), DateHelper.toDate(doiTuongCapNhat.getNgayKhaiGiang(), "dd-MM-yyyy"), doiTuongCapNhat.getGhiChu(), doiTuongCapNhat.getNguoiTao(), DateHelper.toDate(doiTuongCapNhat.getNgayTao(), "dd-MM-yyyy"), doiTuongCapNhat.getMaKhoaHoc());
    }

    @Override
    public void delete(String ma) {
        String sql = "delete from KhoaHoc where makhoahoc = ?";
        JDBCHelper.updateSQL(sql, ma);
    }

    @Override
    public void insert(KhoaHoc doiTuongMoi, String linkAnh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(KhoaHoc doiTuongdoiTuongCapNhat, String ma, String linkAnh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/////////
    public void deletebymaCD(String ma) {
        String sql = "delete from KhoaHoc where maChuyenDe = ?";
        JDBCHelper.updateSQL(sql, ma);
    }

    public void updatebyMaCD(String maChuyeDe, String maChuyenDeMoi) {
        String sql = "UPDATE KHOAHOC SET MaChuyenDe = ? WHERE MaChuyenDe = ?";
        JDBCHelper.updateSQL(sql, maChuyeDe, maChuyenDeMoi);
    }
    // tìm kiếm
    public List<KhoaHoc> selectAll(String ma) {
        // List tạm
        List<KhoaHoc> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select a.MaKhoaHoc, a.MaChuyenDe, a.Hocphi, a.Thoiluong,a.NgayKhaiGiang, a.NguoiTao, a.NgayTao,a.GhiChu from KHOAHOC a inner join CHUYENDE b on a.MaChuyenDe = b.MaChuyenDe \n" +
        "where a.MaKhoaHoc like N'%"+ma+"%' or a.MaChuyenDe like N'%"+ma+"%' or b.TenChuyenDe like N'%"+ma+"%' or a.GhiChu like N'%"+ma+"%'");
        return list;
    }
    
    // 
    public int soKhoaHoc() {
        String sql = "select COUNT(*) from khoahoc";
        return Integer.valueOf(String.valueOf(JDBCHelper.valueSQL(sql)));
    }
    
}
