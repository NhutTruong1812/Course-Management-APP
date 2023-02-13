package DAO_Classes;

import Entity_Classes.NhanVien;
import Entity_Classes.NhanVien;
import Tool_Classes.DateHelper;
import Tool_Classes.ImageHelper;
import Tool_Classes.JDBCHelper;
import java.awt.Image;
import java.sql.ResultSet;
import java.lang.Exception;
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

public class NhanVienDAO implements WolvesEduDAO<NhanVien, String>{
    
    @Override
    public List<NhanVien> selectAll() {
        // List tạm
        List<NhanVien> list = new ArrayList<>();
        
        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from NhanVien");
        
        return list; 
    }
    
    //Tìm kiếm nhân viên
    public List<NhanVien> selectAll(String ma) {
        // List tạm
        List<NhanVien> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from NhanVien where MaNhanVien like N'%" + ma + "%' or HoTen like N'%" + ma + "%' or DiaChi like N'%" + ma + "%' or chucVu like N'%" + ma + "%' or CCCD like N'%" + ma + "%'");

        return list;
    }

    
    @Override
    public NhanVien selectById(String ma) {
        // List tạm
        List<NhanVien> list = new ArrayList<>();
        
        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from NhanVien where MaNhanVien like ? or HoTen like ?", ma);
        
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectBySQL(String sql, Object... thamSo) {
        // List tạm
        List<NhanVien> list = new ArrayList<>();
        
        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL(sql, thamSo);
                
                // Trả kết quả
                while (ketQua.next()) {
                    // Đối tượng chuyên đề
                    NhanVien doiTuong = new NhanVien(ketQua.getString(1), ketQua.getString(2), ketQua.getString(3), DateHelper.toString(ketQua.getDate(4), "dd-MM-yyyy"), ketQua.getString(5), ketQua.getString(6), ketQua.getString(7), ketQua.getBytes(8), ketQua.getString(9), ketQua.getString(10), ketQua.getString(11));
                    
                    // Thêm vào list tạm
                    list.add(doiTuong);
                }
            } finally {
                ketQua.getStatement().getConnection().close();
                JDBCHelper.closeConnectSQL();
            }
        } catch (Exception e) {System.out.println("loi: "+e); }
        
        return list; 
    }

    @Override
    public void insert(NhanVien doiTuongMoi, String linkAnh) {
        String sql = "INSERT INTO NhanVien(MANHANVIEN, HOTEN, GIOITINH, NGAYSINH, SDT, EMAIL, DIACHI,ANH, CCCD, CHUCVU, MATKHAU) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        JDBCHelper.updateSQL(sql, doiTuongMoi.getMaNhanVien(), doiTuongMoi.getHoTen(), doiTuongMoi.getGioiTinh(), DateHelper.toDate(doiTuongMoi.getNgaySinh(), "dd-MM-yyyy"), doiTuongMoi.getSdt(), doiTuongMoi.getEmail(), doiTuongMoi.getDiaChi(), ImageHelper.imageToByte(linkAnh), doiTuongMoi.getCCCD(), doiTuongMoi.getChucVu(), doiTuongMoi.getMatKhau());
    }
    
    
    public void insertNoAvatarNam(NhanVien doiTuongMoi) {
        String sql = "INSERT INTO NhanVien(MANHANVIEN, HOTEN, GIOITINH, NGAYSINH, SDT, EMAIL, DIACHI,ANH, CCCD, CHUCVU, MATKHAU) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        JDBCHelper.updateSQL(sql, doiTuongMoi.getMaNhanVien(), doiTuongMoi.getHoTen(), doiTuongMoi.getGioiTinh(), DateHelper.toDate(doiTuongMoi.getNgaySinh(), "dd-MM-yyyy"), doiTuongMoi.getSdt(), doiTuongMoi.getEmail(), doiTuongMoi.getDiaChi(), ImageHelper.imageToByte("src/image/nhanvien/macDinhNam.jpg"), doiTuongMoi.getCCCD(), doiTuongMoi.getChucVu(), doiTuongMoi.getMatKhau());
    }
    
    public void insertNoAvatarNu(NhanVien doiTuongMoi) {
        String sql = "INSERT INTO NhanVien(MANHANVIEN, HOTEN, GIOITINH, NGAYSINH, SDT, EMAIL, DIACHI,ANH, CCCD, CHUCVU, MATKHAU) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        JDBCHelper.updateSQL(sql, doiTuongMoi.getMaNhanVien(), doiTuongMoi.getHoTen(), doiTuongMoi.getGioiTinh(), DateHelper.toDate(doiTuongMoi.getNgaySinh(), "dd-MM-yyyy"), doiTuongMoi.getSdt(), doiTuongMoi.getEmail(), doiTuongMoi.getDiaChi(), ImageHelper.imageToByte("src/image/nhanvien/macDinhNu.jpg"), doiTuongMoi.getCCCD(), doiTuongMoi.getChucVu(), doiTuongMoi.getMatKhau());
    }

    @Override
    public void update(NhanVien doiTuongCapNhat, String ma, String linkAnh) {
        String sql = "UPDATE NhanVien SET HOTEN = ?, GIOITINH = ?, NGAYSINH = ?, SDT = ?, EMAIL = ?, DIACHI = ?, ANH = ?, CCCD= ?, CHUCVU = ?, MATKHAU = ? WHERE MANHANVIEN = ?";
        JDBCHelper.updateSQL(sql, doiTuongCapNhat.getHoTen(), doiTuongCapNhat.getGioiTinh(), DateHelper.toDate(doiTuongCapNhat.getNgaySinh(), "dd-MM-yyyy"), doiTuongCapNhat.getSdt(), doiTuongCapNhat.getEmail(), doiTuongCapNhat.getDiaChi(), ImageHelper.imageToByte(linkAnh), doiTuongCapNhat.getCCCD(), doiTuongCapNhat.getChucVu(), doiTuongCapNhat.getMatKhau(), doiTuongCapNhat.getMaNhanVien());
    }
    
    public void updateNoAvatarNam(NhanVien doiTuongCapNhat, String ma) {
        String sql = "UPDATE NhanVien SET HOTEN = ?, GIOITINH = ?, NGAYSINH = ?, SDT = ?, EMAIL = ?, DIACHI = ?, ANH = ?, CCCD= ?, CHUCVU = ?, MATKHAU = ? WHERE MANHANVIEN = ?";
        JDBCHelper.updateSQL(sql, doiTuongCapNhat.getHoTen(), doiTuongCapNhat.getGioiTinh(), DateHelper.toDate(doiTuongCapNhat.getNgaySinh(), "dd-MM-yyyy"), doiTuongCapNhat.getSdt(), doiTuongCapNhat.getEmail(), doiTuongCapNhat.getDiaChi(), ImageHelper.imageToByte("src/image/nhanvien/macDinhNam.jpg"), doiTuongCapNhat.getCCCD(), doiTuongCapNhat.getChucVu(), doiTuongCapNhat.getMatKhau(), doiTuongCapNhat.getMaNhanVien());
    }
    
    public void updateNoAvatarNu(NhanVien doiTuongCapNhat, String ma) {
        String sql = "UPDATE NhanVien SET HOTEN = ?, GIOITINH = ?, NGAYSINH = ?, SDT = ?, EMAIL = ?, DIACHI = ?, ANH = ?, CCCD= ?, CHUCVU = ?, MATKHAU = ? WHERE MANHANVIEN = ?";
        JDBCHelper.updateSQL(sql, doiTuongCapNhat.getHoTen(), doiTuongCapNhat.getGioiTinh(), DateHelper.toDate(doiTuongCapNhat.getNgaySinh(), "dd-MM-yyyy"), doiTuongCapNhat.getSdt(), doiTuongCapNhat.getEmail(), doiTuongCapNhat.getDiaChi(), ImageHelper.imageToByte("src/image/nhanvien/macDinhNu.jpg"), doiTuongCapNhat.getCCCD(), doiTuongCapNhat.getChucVu(), doiTuongCapNhat.getMatKhau(), doiTuongCapNhat.getMaNhanVien());
    }
    
    public void updateGiuNguyenAvatar(NhanVien doiTuongCapNhat, String ma) {
        String sql = "UPDATE NhanVien SET HOTEN = ?, GIOITINH = ?, NGAYSINH = ?, SDT = ?, EMAIL = ?, DIACHI = ?, CCCD= ?, CHUCVU = ?, MATKHAU = ? WHERE MANHANVIEN = ?";
        JDBCHelper.updateSQL(sql, doiTuongCapNhat.getHoTen(), doiTuongCapNhat.getGioiTinh(), DateHelper.toDate(doiTuongCapNhat.getNgaySinh(), "dd-MM-yyyy"), doiTuongCapNhat.getSdt(), doiTuongCapNhat.getEmail(), doiTuongCapNhat.getDiaChi(), doiTuongCapNhat.getCCCD(), doiTuongCapNhat.getChucVu(), doiTuongCapNhat.getMatKhau(), doiTuongCapNhat.getMaNhanVien());
    }

    @Override
    public void delete(String ma) {
        String sql = "delete from NhanVien where maNhanVien = ?";
        JDBCHelper.updateSQL(sql, ma);
    }

    @Override
    public void insert(NhanVien doiTuongMoi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(NhanVien doiTuongCapNhat, String ma) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void updatePassByID(String maNhanVien, String matKhau) {
        String sql = "update NHANVIEN set MatKhau = ? where MaNhanVien = ?";
        JDBCHelper.updateSQL(sql, matKhau, maNhanVien);
    }

    public void updateDemo(String maNhanVien, String linkAnh) {
        String sql = "update nhanvien set anh = ? where manhanvien = ?";
        JDBCHelper.updateSQL(sql, ImageHelper.imageToByte(linkAnh), maNhanVien);
    }
    
}
