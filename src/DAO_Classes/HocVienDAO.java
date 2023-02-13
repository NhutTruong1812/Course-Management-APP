package DAO_Classes;

import Entity_Classes.HocVien;
import Entity_Classes.NguoiHoc;
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
public class HocVienDAO implements WolvesEduDAO<HocVien, String> {

    @Override
    public List<HocVien> selectAll() {
        // List tạm
        List<HocVien> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from HocVien");

        return list;
    }

    @Override
    public HocVien selectById(String ma) {
        // List tạm
        List<HocVien> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from HocVien where id = ?", ma);

        return list.get(0);
    }

    @Override
    public List<HocVien> selectBySQL(String sql, Object... thamSo) {
        // List tạm
        List<HocVien> list = new ArrayList<>();

        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL(sql, thamSo);

                // Trả kết quả
                while (ketQua.next()) {
                    // Đối tượng chuyên đề
                    HocVien doiTuong = new HocVien(ketQua.getString(1), ketQua.getString(2), ketQua.getFloat(3));

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
    public void insert(HocVien doiTuongMoi) {
        String sql = "INSERT INTO HocVien(MANGUOIHOC, MAKHOAHOC, DIEMTB) VALUES (?,?,?)";
        JDBCHelper.updateSQL(sql, doiTuongMoi.getMaNguoiHoc(), doiTuongMoi.getMaKhoaHoc(), doiTuongMoi.getDiemTB());
    }

    @Override
    public void update(HocVien doiTuongCapNhat, String ma) {
        String sql = "UPDATE HocVien SET MAKHOAHOC = ? , DIEMTB = ? WHERE MANGUOIHOC = ?";
        JDBCHelper.updateSQL(sql, doiTuongCapNhat.getMaKhoaHoc(), doiTuongCapNhat.getDiemTB(), doiTuongCapNhat.getMaNguoiHoc());
    }

    @Override
    public void delete(String ma) {
        
    }

    @Override
    public void insert(HocVien doiTuongMoi, String linkAnh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(HocVien doiTuongCapNhat, String ma, String linkAnh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // 
    public List<Object[]> selectHV_HV(String maKhoaHoc) {
        // List tạm
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL("select hv.MaKhoaHoc, hv.MaNguoiHoc, nh.HoTen, hv.DiemTB from HOCVIEN hv join NGUOIHOC nh on nh.MaNguoiHoc = hv.MaNguoiHoc where makhoahoc = ?", maKhoaHoc);
                while (ketQua.next()) {
                    Object[] row = {
                        ketQua.getString(1),
                        ketQua.getString(2),
                        ketQua.getString(3),
                        ketQua.getFloat(4),
                    };
                    list.add(row);
                }
            } catch (Exception e) {
                System.out.println("loi hv_hv: " + e);
            } finally {
                ketQua.getStatement().getConnection().close();
                JDBCHelper.closeConnectSQL();
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    // 
    public List<Object[]> selectHV_HV_TimKiem(String maKhoaHoc, String timKiem) {
        // List tạm
        List<Object[]> list = new ArrayList<>();
        String sql = "select hv.MaKhoaHoc, hv.MaNguoiHoc, nh.HoTen, hv.DiemTB from HOCVIEN hv join NGUOIHOC nh on nh.MaNguoiHoc = hv.MaNguoiHoc where makhoahoc = ? and (nh.hoten LIKE N'%" + timKiem + "%' OR hv.MaNguoiHoc LIKE N'%"+ timKiem + "%')";
        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL(sql, maKhoaHoc);
                while (ketQua.next()) {
                    Object[] row = {
                        ketQua.getString(1),
                        ketQua.getString(2),
                        ketQua.getString(3),
                        ketQua.getFloat(4),
                    };
                    list.add(row);
                }
            } catch (Exception e) {
                System.out.println("loi hv_hv: " + e);
            } finally {
                ketQua.getStatement().getConnection().close();
                JDBCHelper.closeConnectSQL();
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    // 
    public List<NguoiHoc> selectHV_NH(String maKhoaHoc) {
        // List tạm
        List<NguoiHoc> list = new ArrayList<>();

        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL("select MaNguoiHoc, HoTen, GioiTinh, NgaySinh, SDT, Email, DiaChi from NGUOIHOC where MaNguoiHoc not in (select hv.MaNguoiHoc from HOCVIEN hv join NGUOIHOC nh on nh.MaNguoiHoc = hv.MaNguoiHoc where MaKhoaHoc = ?)", maKhoaHoc);

                // Trả kết quả
                while (ketQua.next()) {
                    // Đối tượng chuyên đề
                    NguoiHoc doiTuong = new NguoiHoc(ketQua.getString(1), ketQua.getString(2), ketQua.getString(3), DateHelper.toString(ketQua.getDate(4), "dd-MM-yyyy"), ketQua.getString(5), ketQua.getString(6), ketQua.getString(7));

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
    public List<NguoiHoc> selectHV_NH_TimKiem(String maKhoaHoc, String timKiem) {
        // List tạm
        List<NguoiHoc> list = new ArrayList<>();
        String sql = "select MaNguoiHoc, HoTen, GioiTinh, NgaySinh, SDT, Email, DiaChi from NGUOIHOC where MaNguoiHoc not in (select hv.MaNguoiHoc from HOCVIEN hv join NGUOIHOC nh on nh.MaNguoiHoc = hv.MaNguoiHoc where MaKhoaHoc = ?) and (hoten LIKE N'%" + timKiem + "%' OR MaNguoiHoc LIKE N'%"+ timKiem + "%')";
        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL(sql, maKhoaHoc);

                // Trả kết quả
                while (ketQua.next()) {
                    // Đối tượng chuyên đề
                    NguoiHoc doiTuong = new NguoiHoc(ketQua.getString(1), ketQua.getString(2), ketQua.getString(3), DateHelper.toString(ketQua.getDate(4), "dd-MM-yyyy"), ketQua.getString(5), ketQua.getString(6), ketQua.getString(7));

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
    public void deleteHV_HV(String maKhoaHoc, String maNguoiHoc) {
        String sql = "delete from HocVien where maKhoaHoc = ? and maNguoiHoc = ?";
        JDBCHelper.updateSQL(sql, maKhoaHoc, maNguoiHoc);
    }
    
    // 
    public void updateHV_HV(float diem, String maKhoaHoc, String maNguoiHoc) {
        String sql = "UPDATE HocVien SET DIEMTB = ?  where MaKhoaHoc = ? and MaNguoiHoc = ?";
        JDBCHelper.updateSQL(sql, diem, maKhoaHoc, maNguoiHoc);
    }
    
    // 
    public int soHocVien() {
        String sql = "select COUNT(*) from HOCVIEN";
        return Integer.valueOf(String.valueOf(JDBCHelper.valueSQL(sql)));
    }

    public void deleteHV(String ma) {
        String sql = "delete from HocVien where maKhoaHoc = ?";
        JDBCHelper.updateSQL(sql, ma);
    }
    
}
