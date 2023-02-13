package DAO_Classes;

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
 * VeketQuaion: 1.0.0
 * Author: Four Wolves >> Văn Hữu Đức - PC01395 >> Trần Thanh Hồ - PC02096 >> Huỳnh Nhật Quang - PC01597 >> Nguyễn Văn Nhựt Trường - PC01752
 * Date: 17th September 2021 >> .. .. 2021
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
public class ThongKeDAO {

    // Phương thức thống kê số lượng người học từng chuyên đề
    public List<Object[]> tk1(String thoiGian) {
        String sql;
        if (thoiGian.equalsIgnoreCase("nam")) {
            sql = "{call sp_TKNguoiHoc_nam}";
        } else if (thoiGian.equalsIgnoreCase("quy")) {
            sql = "{call sp_TKNguoiHoc_quy}";
        } else {
            sql = "{call sp_TKNguoiHoc_thang}";
        }

        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL(sql);
                while (ketQua.next()) {
                    Object[] row = {
                        ketQua.getString(1),
                        ketQua.getInt(2),
                        DateHelper.toString(ketQua.getDate(3), "dd-MM-yyyy"),
                        DateHelper.toString(ketQua.getDate(4), "dd-MM-yyyy")
                    };
                    list.add(row);
                }
            } catch (Exception e) {
                System.out.println("loi tk1: " + e);
            } finally {
                ketQua.getStatement().getConnection().close();
                JDBCHelper.closeConnectSQL();
            }
        } catch (Exception e) {
        }
        return list;
    }

    // Phương thức thống kê điểm từng khóa học
    public List<Object[]> tk2(String MaKhoaHoc) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL("{call sp_TKDiem_KhoaHoc (?)}", MaKhoaHoc);
                while (ketQua.next()) {
                    double diem = ketQua.getDouble(3);
                    String xepLoai = "Xuất sắc";
                    if (diem < 0) {
                        xepLoai = "Điểm không hợp lệ";
                    } else if (diem < 3) {
                        xepLoai = "Kém";
                    } else if (diem < 5) {
                        xepLoai = "Yếu";
                    } else if (diem < 6.5) {
                        xepLoai = "Trung bình";
                    } else if (diem < 7.5) {
                        xepLoai = "Khá";
                    } else if (diem < 9) {
                        xepLoai = "Giỏi";
                    }
                    Object[] row = {
                        ketQua.getString(1),
                        ketQua.getString(2),
                        diem,
                        xepLoai
                    };
                    list.add(row);
                }
            } finally {
                ketQua.getStatement().getConnection().close();
                JDBCHelper.closeConnectSQL();
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    // Phương thức thống kê điểm từng chuyên đề
    public List<Object[]> tk3(int nam) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL("{call sp_TKDiem_ChuyenDe (?)}", nam);
                while (ketQua.next()) {
                    Object[] row = {
                        ketQua.getString("ChuyenDe"),
                        ketQua.getInt("SoHV"),
                        ketQua.getDouble("ThapNhat"),
                        ketQua.getDouble("CaoNhat"),
                        ketQua.getDouble("TrungBinh")
                    };
                    list.add(row);
                }
            } finally {
                ketQua.getStatement().getConnection().close();
                JDBCHelper.closeConnectSQL();
            }
        } catch (SQLException ex) {
        }
        return list;
    }

    // Phương thức thống kê doanh thu từng chuyên đề
    public List<Object[]> tk4(int nam) {
        List<Object[]> list = new ArrayList<>();

        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL("{call sp_TKDoanhThu (?)}", nam);
                while (ketQua.next()) {
                    Object[] row = {
                        ketQua.getString("ChuyenDe"),
                        ketQua.getInt("SoKH"),
                        ketQua.getInt("SoHV"),
                        (long) ketQua.getDouble("DoanhThu"),
                        (long) ketQua.getDouble("ThapNhat"),
                        (long) ketQua.getDouble("CaoNhat"),
                        (long) ketQua.getDouble("TrungBinh")
                    };
                    list.add(row);
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
    public List<Object[]> cboChuyenDe() {
        List<Object[]> list = new ArrayList<>();

        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL("select TenChuyenDe, machuyende from CHUYENDE");

                while (ketQua.next()) {
                    Object[] row = {
                        ketQua.getString(1),
                        ketQua.getString(2)
                    };
                    list.add(row);
                }
            } catch (Exception e) {
                System.out.println("loi: " + e);
            } finally {
                ketQua.getStatement().getConnection().close();
                JDBCHelper.closeConnectSQL();
            }
        } catch (SQLException e) {
        }
        return list;
    }

    // 
    public List<Object[]> cboKhoaHoc(String maChuyenDe) {
        List<Object[]> list = new ArrayList<>();
        String sql = "select MaKhoaHoc from KHOAHOC kh join CHUYENDE cd on cd.MaChuyenDe = kh.MaChuyenDe where cd.MaChuyenDe like ?";
        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL(sql, maChuyenDe);

                while (ketQua.next()) {
                    Object[] row = {
                        ketQua.getString(1)
                    };
                    list.add(row);
                }
            } catch (Exception e) {
                System.out.println("loi cbo: " + e);
            } finally {
                ketQua.getStatement().getConnection().close();
                JDBCHelper.closeConnectSQL();
            }
        } catch (SQLException e) {
        }
        return list;
    }

    // 
    public List<Object[]> cboNam() {
        List<Object[]> list = new ArrayList<>();

        try {
            ResultSet ketQua = null;
            try {
                ketQua = JDBCHelper.querySQL("select YEAR(NgayKhaiGiang) from KHOAHOC group by YEAR(NgayKhaiGiang)");

                while (ketQua.next()) {
                    Object[] row = {
                        ketQua.getInt(1)
                    };
                    list.add(row);
                }
            } catch (Exception e) {
                System.out.println("loi: " + e);
            } finally {
                ketQua.getStatement().getConnection().close();
                JDBCHelper.closeConnectSQL();
            }
        } catch (SQLException e) {
        }
        return list;
    }

    // 
}
