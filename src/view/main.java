package view;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Topic: Cửa sổ chính của phần mềm
 * Content: Phần mềm quản lý hệ thống đào tạo - WolvesEdu
            * Chức năng quản lý: chuyên đề, khóa học, người học, học viên và nhân viên
            * Chức năng thống kê: số lượng người học theo thời gian, điểm học viên từng khóa học, điểm tổng kết từng chuyên đề, doanh thu từng chuyên đề
            * Phân quyền: trưởng phòng (tất cả chức năng) và nhân viên (không được xóa và thống kê)
 * Version: 1.0.0
 * Author: Four Wolves >> Văn Hữu Đức - PC01395 >> Trần Thanh Hồ - PC02096 >> Huỳnh Nhật Quang - PC01597 >> Nguyễn Văn Nhựt Trường - PC01752
 * Date: 17th September 2021 >> 2021 19th October 2021
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import Entity_Classes.*;
import DAO_Classes.*;
import Tool_Classes.Auth;
import Tool_Classes.DateHelper;
import Tool_Classes.MessageHelper;
import Tool_Classes.ImageHelper;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class main extends javax.swing.JFrame {

    /**
     * Creates new form main
     */
    public main() {
        initComponents();
        // Trang trí
        trangTri();
        // Tải list 
        taiListAll();
        // Trang chủ
        trangChu();

        // Ngày hôm nay      
        txt3A_NgayTao.setText(String.valueOf(localDate.getDayOfMonth()) + "-" + String.valueOf(localDate.getMonth().getValue()) + "-" + String.valueOf(localDate.getYear()));
        txt4A_NgayDangKy.setText(String.valueOf(localDate.getDayOfMonth()) + "-" + String.valueOf(localDate.getMonth().getValue()) + "-" + String.valueOf(localDate.getYear()));
    }
    /*  TỔ CHỨC TRONG CODE TRONG SOURCE 
        0 THUỘC TÍNH MẶC ĐỊNH
            0.1 BIẾN, THUỘC TÍNH, ĐỐI TƯỢNG MẶC ĐỊNH
            0.2 PHƯƠNG THỨC TIỆN ÍCH DÙNG CHUNG
            0.3 CHÀO MỪNG
        1 ĐĂNG NHẬP
        2 QUÊN MẬT KHẨU
        3 ĐỔI MẬT KHẨU
        4 TRANG CHỦ
        5 QUẢN LÝ CHUYÊN ĐỀ
        6 QUẢN LÝ KHÓA HỌC
        7 QUẢN LÝ NGƯỜI HỌC
        8 QUẢN LÝ HỌC VIÊN
        9 QUẢN LÝ NHÂN VIÊN
        10 THỐNG KÊ TỔNG HỢP
        11 TRỢ GIÚP
            10.1 HƯỚNG DẪN
            10.2 GIỚI THIỆU
            10.3 BÁO CÁO
     */

//______________________________________________________________________________________________________________________________________________________
/*                                                         0 THUỘC TÍNH MẶC ĐỊNH                                                                    */
//______________________________________________________________________________________________________________________________________________________
// _____________________________________0.1 BIẾN, THUỘC TÍNH, ĐỐI TƯỢNG MẶC ĐỊNH
    // Màu sắc
    Color mauMacDinh1B = new Color(0, 0, 0); // Màu chữ đen
    Color mauMacDinh1 = new Color(255, 255, 255); // Khi exit mouse tab menu
    Color mauMacDinh1H = new Color(200, 248, 255); // Khi enter mouse tab menu
    Color mauMacDinh1C = new Color(74, 233, 251); // Khi click mouse tab menu
    Color mauMacDinh2 = new Color(242, 242, 242); // Màu mặc định Quản lý - Thống kê
    Color mauMacDinh2H = new Color(200, 248, 255); // Khi enter mouse tab Quản lý - Thống kê
    CardLayout CaLayout; // CardLayout dùng chung
    // Link ảnh
    String linkAnhChuyenDe = "";
    String linkAnhNguoiHoc = "";
    String linkAnhNhanVien = "";
    String linkAnhDung = "image/icon/dung.png";
    String linkAnhSai = "image/icon/sai.png";
    String linkAnhHoi = "image/icon/hoi.png";
    int maKhoiPhuc;
    // List
    List<ChuyenDe> listChuyenDe = new ArrayList<>();
    List<KhoaHoc> listKhoaHoc = new ArrayList<>();
    List<NguoiHoc> listNguoiHoc = new ArrayList<>();
    List<Object[]> listHocVien_HV = new ArrayList<>();
    List<HocVien> listHocVien_HV2 = new ArrayList<>();
    List<Float> listDiem = new ArrayList<>();
    List<NguoiHoc> listHocVien_NH = new ArrayList<>();
    List<NhanVien> listNhanVien = new ArrayList<>();
    List<Object[]> listTK1 = new ArrayList<>();
    List<Object[]> listTK2 = new ArrayList<>();
    List<Object[]> listTK2_CD = new ArrayList<>();
    List<Object[]> listTK3 = new ArrayList<>();
    List<Object[]> listTK4 = new ArrayList<>();
    List<String> listGhiNho = new ArrayList<>();
    DefaultTableModel model;
    // DAO
    ChuyenDeDAO ChuyenDeDAO = new ChuyenDeDAO();
    KhoaHocDAO KhoaHocDAO = new KhoaHocDAO();
    NguoiHocDAO NguoiHocDAO = new NguoiHocDAO();
    HocVienDAO HocVienDAO = new HocVienDAO();
    NhanVienDAO NhanVienDAO = new NhanVienDAO();
    ThongKeDAO ThongKeDAO = new ThongKeDAO();
    GhiNhoDAO GhiNhoDao = new GhiNhoDAO();
    // Vị trí của chuyên đề, khóa học, người học, nhân viên
    int row = -1;
    LocalDate localDate = LocalDate.now();

// __________________________________________0.2 PHƯƠNG THỨC TIỆN ÍCH DÙNG CHUNG
    // Frame chính: đổi icon
    private void doiIcon_MacDinh() {
        setIconImage(new ImageIcon("image/UI/main.png").getImage());
    }

    // Menu trái: đổi màu tab menu
    private void doiMau_MacDinh(JLabel lbl) {
        // Màu nền mặc định
        lbl.setBackground(mauMacDinh1);
        // Màu chữ mặc định
        lbl.setForeground(mauMacDinh1B);
        // Font chữ mặc định
        lbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
    }

    // Menu trái: đổi màu & icon cho 9 tab menu
    private void doiMau_MacDinhAll() {
        doiMau_MacDinh(lblTrangChu);
        doiMau_MacDinh(lblQLChuyenDe);
        doiMau_MacDinh(lblQLKhoaHoc);
        doiMau_MacDinh(lblQLNguoiHoc);
        doiMau_MacDinh(lblQLHocVien);
        doiMau_MacDinh(lblQLNhanVien);
        doiMau_MacDinh(lblTKTongHop);
        doiMau_MacDinh(lblBaoCao);
        doiMau_MacDinh(lblDangXuat);
        // Icon mặc định
        lblTrangChu.setIcon(new ImageIcon("image/icon/72x36/trangchu.png"));
        lblQLChuyenDe.setIcon(new ImageIcon("image/icon/72x36/chuyende.png"));
        lblQLKhoaHoc.setIcon(new ImageIcon("image/icon/72x36/khoahoc.png"));
        lblQLNguoiHoc.setIcon(new ImageIcon("image/icon/72x36/nguoihoc.png"));
        lblQLHocVien.setIcon(new ImageIcon("image/icon/72x36/hocvien.png"));
        lblQLNhanVien.setIcon(new ImageIcon("image/icon/72x36/nhanvien.png"));
        lblTKTongHop.setIcon(new ImageIcon("image/icon/72x36/thongke.png"));
        lblBaoCao.setIcon(new ImageIcon("image/icon/72x36/baocao.png"));
        lblDangXuat.setIcon(new ImageIcon("image/icon/72x36/dangxuat.png"));
    }

    // Menu trái: chọn tab mắc định
    private void chonTab_MacDinh(JLabel lbl) {
        // Tab menu
        lbl.setBackground(mauMacDinh1C);
        lbl.setForeground(mauMacDinh1);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 18));
    }

    // Menu trái: chọn tất cả tab mặc định
    private void chonTab_MacDinhAll() {
        chonTab_MacDinh(lblTrangChu);
        lblTrangChu.setIcon(new ImageIcon("image/icon/72x36/trangchu_hov.png"));
        chonTab_MacDinh(lblQLChuyenDe_CapNhat);
        chonTab_MacDinh(lblQLKhoaHoc_CapNhat);
        chonTab_MacDinh(lblQLNguoiHoc_CapNhat);
        chonTab_MacDinh(lblQLHocVien_HocVien);
        chonTab_MacDinh(lblQLNhanVien_CapNhat);
        chonTab_MacDinh(lblTK1);
    }

    // Menu trái: chuyển tab CardLayout
    private void chuyenTab(JPanel pnl, String cardName) {
        CaLayout = (CardLayout) pnl.getLayout();
        CaLayout.show(pnl, cardName);
    }

    // Menu trái: đổi màu khi entermouse
    private void doiMau_Menu_Enter(JLabel lbl) {
        if (lbl.getBackground() == mauMacDinh1) {
            lbl.setBackground(mauMacDinh1H); // Đổi màu nền
        } else if (lbl.getBackground() == mauMacDinh2) {
            lbl.setBackground(mauMacDinh2H); // Đổi màu nền
        }
    }

    // Menu trái: đổi màu khi exitmouse
    private void doiMau_Menu_Exit(JLabel lbl) {
        if (lbl.getBackground() == mauMacDinh1H) {
            lbl.setBackground(mauMacDinh1);
        } else if (lbl.getBackground() == mauMacDinh2H) {
            lbl.setBackground(mauMacDinh2); // Đổi màu nền
        }
    }

    // Menu trái: đổi màu khi clickmouse
    private void doiMau_Menu_Click(JLabel lbl) {
        doiMau_MacDinhAll();
        String temp = String.valueOf(lbl.getIcon());
        temp = temp.replace(".", "_hov.");
        lbl.setBackground(mauMacDinh1C);
        lbl.setForeground(mauMacDinh1);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 18));
        lbl.setIcon(new ImageIcon(temp));
        // Cập nhật trạng thái
        temp = lbl.getText().trim();
        lblTrangThai.setText(temp);
    }

    // Table: định dạng cho 1 bảng
    private void dinhDang_Bang(JTable tbl) {
        JTableHeader tblHeader = tbl.getTableHeader(); // Lấy model thanh đầu của bảng
        tblHeader.setFont(new Font("Tahoma", Font.BOLD, 16)); // Chỉnh font chữ
        ((DefaultTableCellRenderer) tblHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER); // Canh giữa
        tblHeader.setPreferredSize(new Dimension(0, 50));// Canh biên
        // Cen-Align for header
        DefaultTableCellRenderer canhGiua = new DefaultTableCellRenderer(); // Lấy thuộc tính canh giữa
        canhGiua.setHorizontalAlignment(JLabel.CENTER); // Canh giữa
        tbl.setDefaultRenderer(Object.class, canhGiua);// Canh giữa
    }

    // Table: định dạng các bảng
    private void dinhDang_BangAll() {
        dinhDang_Bang(tblQLChuyenDe);
        dinhDang_Bang(tblQLKhoaHoc);
        dinhDang_Bang(tblQLNguoiHoc);
        dinhDang_Bang(tblQLHocVien_HocVien);
        dinhDang_Bang(tblQLHocVien_NguoiHoc);
        dinhDang_Bang(tblQLNhanVien);
        dinhDang_Bang(tblTKNguoiHoc);
        dinhDang_Bang(tblTKDiem_KhoaHoc);
        dinhDang_Bang(tblTKDiem_ChuyenDe);
        dinhDang_Bang(tblTKDoanhThu_ChuyenDe);
    }

    // Quản lý - Thống kê: đổi màu mặc định tab Quản lý - Thống kê
    private void doiMau_QLTK(JLabel lbl) {
        // Màu nền mặc định
        lbl.setBackground(mauMacDinh2);
        // Màu chữ mặc định
        lbl.setForeground(mauMacDinh1B);
        // Font chữ mặc định
        lbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
    }

    // Quản lý - Thống kê: đổi màu cho 14 tab Quản lý - Thống kê
    private void doiMau_QLTKAll() {
        doiMau_QLTK(lblQLChuyenDe_CapNhat);
        doiMau_QLTK(lblQLChuyenDe_DanhSach);
        doiMau_QLTK(lblQLKhoaHoc_CapNhat);
        doiMau_QLTK(lblQLKhoaHoc_DanhSach);
        doiMau_QLTK(lblQLNguoiHoc_CapNhat);
        doiMau_QLTK(lblQLNguoiHoc_DanhSach);
        doiMau_QLTK(lblQLHocVien_HocVien);
        doiMau_QLTK(lblQLHocVien_NguoiHoc);
        doiMau_QLTK(lblQLNhanVien_CapNhat);
        doiMau_QLTK(lblQLNhanVien_DanhSach);
        doiMau_QLTK(lblTK1);
        doiMau_QLTK(lblTK2);
        doiMau_QLTK(lblTK3);
        doiMau_QLTK(lblTK4);
    }

    // Quản lý - Thống kê: đổi màu khi clickmouse
    private void doiMau_QLTK_Click(JLabel lbl1, JLabel lbl2) {
        // lbl được chọn
        lbl1.setBackground(mauMacDinh1C);
        lbl1.setForeground(mauMacDinh1);
        lbl1.setFont(new Font("Tahoma", Font.BOLD, 18));
        // lbl không được chọn
        lbl2.setBackground(mauMacDinh2);
        lbl2.setForeground(mauMacDinh1B);
        lbl2.setFont(new Font("Tahoma", Font.PLAIN, 18));
    }

    // Trang trí tổng hợp
    private void trangTri() {
        doiMau_MacDinhAll();
        doiMau_QLTKAll();
        doiIcon_MacDinh();
        chonTab_MacDinhAll();
        dinhDang_BangAll();
    }

    // Hiển thị: frame được truyền vào
    private void hienthiFrame(JFrame frm) {
        frm.setVisible(true);
        frm.setResizable(false);
        frm.setLocationRelativeTo(null);
        frm.setIconImage(new ImageIcon("image/UI/main.png").getImage());
    }

    // Hiển thị: dialog được truyền vào
    private void hienThiDialog(JDialog dlg) {
        dlg.setVisible(true);
        dlg.setLocationRelativeTo(null);
        dlg.setIconImage(new ImageIcon("image/UI/main.png").getImage());
    }

    // Test: tải ảnh demo để thử chương trình lúc đầu
    private void taiAnhdemo() {
        if (listNhanVien.get(0).getAnh() == null) {
            NhanVienDAO.updateDemo("NV01", "image/nhanvien/nv01.jpg");
            NhanVienDAO.updateDemo("NV02", "image/nhanvien/nv02.jpg");
            NhanVienDAO.updateDemo("NV03", "image/nhanvien/nv03.jpg");
            NhanVienDAO.updateDemo("NV04", "image/nhanvien/nv04.jpg");
            taiListNhanVien();
        }
        if (listNguoiHoc.get(0).getAnh() == null) {
            NguoiHocDAO.updateDemo("NH01", "image/nguoihoc/nh01.jpg");
            NguoiHocDAO.updateDemo("NH02", "image/nguoihoc/nh02.jpg");
            NguoiHocDAO.updateDemo("NH03", "image/nguoihoc/nh03.jpg");
            NguoiHocDAO.updateDemo("NH04", "image/nguoihoc/nh04.jpg");
            taiListNguoiHoc();
        }
        if (listChuyenDe.get(0).getAnh() == null) {
            ChuyenDeDAO.updateDemo("WEP", "image/chuyende/cd01.jpg");
            ChuyenDeDAO.updateDemo("PSD", "image/chuyende/cd02.jpg");
            ChuyenDeDAO.updateDemo("JAB", "image/chuyende/cd03.jpg");
            ChuyenDeDAO.updateDemo("PYB", "image/chuyende/cd04.jpg");
            taiListChuyenDe();
        }
    }

    // Kiểm tra ảnh chuyên đề
    private String kiemTraAnh(Image anh) {
        String ketQua = "";
        if (anh == null) {
            ketQua = "Chưa có ảnh";
        } else {
            ketQua = "Đã có ảnh";
        }
        return ketQua;
    }

    // Kiểm tra ngày
    private boolean kiemTraNgay(String ngay) {
        ngay = ngay.trim();
        ngay = doiKieuNgay(ngay);
        String mauNgay = "(((((0[0-9]{1})|([1-2]{1}[0-9]{1})|(3[0-1]{1}))-(01|03|05|07|08|([1]{1}[0]{1})|([1]{1}[2]{1})))|(((0[0-9]{1})|([1-2]{1}[0-9]{1})|(3[0]{1}))-(04|06|09|([1]{1}[1]{1})))|(((0[0-9]{1})|([1-2]{1}[0-8]{1}))-02))-((19[0-9]{2})|(20[0-1]{1}[0-9]{1})|(202[0-9]{1})))|(((0[0-9]{1})|([1-2]{1}[0-9]{1}))-02-(19|(2[0-9]{1}))(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96))";
        if (ngay.matches(mauNgay)) {
            return true;
        }
        return false;
    }

    // Biến đổi ngày - / .\
    private String doiKieuNgay(String ngayDoi) {
        String kytu = "[.///.]{1}";
        ngayDoi = ngayDoi.trim();
        String ngayMoi = "";
        for (int i = 0; i < ngayDoi.length(); i++) {
            if (ngayDoi.substring(i, i + 1).matches(kytu)) {
                ngayMoi = ngayMoi + "-";
            } else {
                ngayMoi = ngayMoi + ngayDoi.substring(i, i + 1);
            }
        }
        return ngayMoi;
    }

    // Kiểm tra tên
    private boolean kiemTraTen(String ten) {
        String tenCheck = "[0-9.]{1}";
        for (int i = 0; i < ten.length(); i++) {
            if (ten.substring(i, i + 1).matches(tenCheck)) {
                return false;
            }
        }
        return true;
    }

    // Kiểm tra ngày lớn nhỏ
    private boolean xetNgay(String ngayXet, String lonHayNho, String ngayChuan) {
        SimpleDateFormat dinhDang = new SimpleDateFormat();
        try {
            dinhDang.applyPattern("dd-MM-yyyy");

            if (ngayChuan.contains("now")) {
                Date now = new Date();
                // ngày
                int ng = now.getDate();
                String ngay;
                if (ng < 10) {
                    ngay = "0" + ng;
                } else {
                    ngay = ng + "";
                }
                // tháng
                int th = now.getMonth() + 1;
                String thang;
                if (th < 10) {
                    thang = "0" + th;
                } else {
                    thang = th + "";
                }
                // năm
                String nam = String.valueOf(now);
                nam = nam.substring(nam.lastIndexOf(" ") + 1, nam.length());
                //
                if (ngayChuan.length() > 3) {
                    String temp = ngayChuan.substring(3, ngayChuan.length());
                    nam = String.valueOf(Integer.valueOf(nam) - Integer.valueOf(temp));
                }

                ngayChuan = ngay + "-" + thang + "-" + nam;
            }
            ngayXet = ngayXet.trim();
            ngayChuan = ngayChuan.trim();

            ngayXet = doiKieuNgay(ngayXet);

            Date dateXet = dinhDang.parse(ngayXet);
            Date dateChuan = dinhDang.parse(ngayChuan);

            int ketQua = dateXet.compareTo(dateChuan);
            // ketQua > 0: 
            // ketQua < 0: 
            // ketQua = 0: 
            if (lonHayNho.equalsIgnoreCase("lon")) {
                if (ketQua >= 0) {
                    return true;
                }
            } else if (lonHayNho.equalsIgnoreCase("nho")) {
                if (ketQua <= 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("loi so sanh ngay: " + e);
        }
        return false;
    }

    // 
// ________________________________________________________________0.3 CHÀO MỪNG
    // sử dụng luồng 
    Timer taiChaoMung, taiMau;
    public static int Green = 255, xetGreen = 1;
    int phanTram = 0;

    // Phương thức tải chào mừng
    private void chaoMung() {
        taiChaoMung = new Timer(14, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prgA1.setValue(phanTram);
                phanTram++;
                if (prgA1.getValue() == 100) {
                    taiChaoMung.stop();
                    JDialog_ChaoMung.setVisible(false);
                    hienthiFrame(JFrame_DangNhap);
                    taiChaoMung.stop();
                }
            }
        });
        taiChaoMung.start();
        taiMau = new Timer(2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (xetGreen == 0) {
                    if (Green == 255) {
                        xetGreen = 1;
                    } else {
                        Green++;
                    }
                } else if (xetGreen == 1) {
                    if (Green == 20) {
                        xetGreen = 0;
                        Green++;
                    } else {
                        Green--;
                    }
                }
                pnlA1.setBackground(new Color(Green, 255, 240));
            }
        });
        taiMau.start();
    }

//______________________________________________________________________________________________________________________________________________________
/*                                                                   1 ĐĂNG NHẬP                                                                   */
//______________________________________________________________________________________________________________________________________________________
    // Ẩn hiện mật khẩu
    private void hienMatKhau(JLabel lbl, JPasswordField pwd) {
        if (lbl.getIcon().toString().contains("A")) {
            pwd.setEchoChar((char) 0);
            lbl.setIcon(new ImageIcon("image/UI/lblHien.png"));
        } else {
            pwd.setEchoChar('●');
            lbl.setIcon(new ImageIcon("image/UI/lblAn.png"));
        }
    }

    // Tải list ghi nhớ mật khẩu
    private void taiListGhiNho() {
        listGhiNho = GhiNhoDao.selectBySQL("select * from ghinho");
        if (listGhiNho.size() > 0) {
            txtB1_TaiKhoan.setText(listGhiNho.get(0));
            pwdB1_MatKhau.setText(listGhiNho.get(1));
            if (listGhiNho.get(1).equals("")) {
                chkB1.setSelected(false);
            } else {
                chkB1.setSelected(true);
            }
        }
    }

    //Phương thức thực hiện đăng nhập
    private void dangNhap() {
        try {
            String taiKhoan = txtB1_TaiKhoan.getText().trim();
            String matKhau = pwdB1_MatKhau.getText().trim();

            // Lỗi trống
            if (taiKhoan.isEmpty() || matKhau.isEmpty()) {
                MessageHelper.alert(this, "KHÔNG ĐƯỢC ĐỂ TRỐNG!", "THÔNG BÁO", linkAnhSai);
                return;
            }

            // Xét
            for (int i = 0; i < listNhanVien.size(); i++) {
                if (listNhanVien.get(i).getMaNhanVien().equalsIgnoreCase(taiKhoan) && listNhanVien.get(i).getMatKhau().equals(matKhau)) {
                    MessageHelper.alert(this, "ĐĂNG NHẬP THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                    ghiNhoToi();
                    // Xét nhân viên đăng nhập
                    JFrame_DangNhap.setVisible(false);
                    // Mở trang chủ
                    main trangChu = new main();
                    trangChu.setVisible(true);
                    trangChu.lblMaNhanVien.setText(taiKhoan.toUpperCase());
                    trangChu.lblWelcome.setText("Welcome, " + taiKhoan.toUpperCase());
                    trangChu.lblAnhDaiDien.setIcon(new ImageIcon(ImageHelper.anhChuan(ImageHelper.byteToImage(listNhanVien.get(i).getAnh()), lblAnhDaiDien.getWidth(), lblAnhDaiDien.getHeight())));
                    break;
                }
                if (i == listNhanVien.size() - 1 && !taiKhoan.equalsIgnoreCase("NV00")) {
                    MessageHelper.alert(this, "SAI TÀI KHOẢN HOẶC MẬT KHẨU", "THÔNG BÁO", linkAnhSai);
                }
            }

            // Tài khoản mặc định
            if (taiKhoan.equalsIgnoreCase("NV00")) {
                ghiNhoToi();
                MessageHelper.alert(this, "ĐĂNG NHẬP THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                JFrame_DangNhap.setVisible(false);
                // Ghi nhớ tôi
                // Mở trang chủ
                main trangChu = new main();
                trangChu.setVisible(true);
            }
        } catch (Exception e) {
        }
    }

    // Ghi nhớ tôi
    private void ghiNhoToi() {
        String taiKhoan = txtB1_TaiKhoan.getText().trim();
        String matKhau = pwdB1_MatKhau.getText().trim();
        
        if (chkB1.isSelected()) {
            if (listGhiNho.size() > 0) {
                GhiNhoDao.update(taiKhoan, matKhau);
            } else {
                GhiNhoDao.insert(taiKhoan, matKhau);
            }
        } else {
            if (listGhiNho.size() > 0) {
                GhiNhoDao.update(taiKhoan, "");
            }
        }
    }

    // Phương thức xét nhân viên đăng nhập
    private void xetNhanVien() {
        try {
            String maNhanVien = txtB1_TaiKhoan.getText().trim();
            for (int i = 0; i < listNhanVien.size(); i++) {
                if (maNhanVien.equalsIgnoreCase(listNhanVien.get(i).getMaNhanVien())) {
                    Auth.user = listNhanVien.get(i);
                    break;
                }
            }
            if (Auth.user.getChucVu().equalsIgnoreCase("nhân viên")) {
                lblTK4.setVisible(false);
                mniTK4.setVisible(false);
                btn2A_Xoa.setVisible(false);
                btn3A_Xoa.setVisible(false);
                btn4A_Xoa.setVisible(false);
                btn5A_Xoa.setVisible(false);
                btn6A_Xoa.setVisible(false);
                lbl6A_HienMK.setVisible(false);
                pwd6A_MatKhau.setEditable(false);
            } else {
                lblTK4.setVisible(true);
                mniTK4.setVisible(true);
                btn2A_Xoa.setVisible(true);
                btn3A_Xoa.setVisible(true);
                btn4A_Xoa.setVisible(true);
                btn5A_Xoa.setVisible(true);
                btn6A_Xoa.setVisible(true);
                lbl6A_HienMK.setVisible(true);
                pwd6A_MatKhau.setEditable(true);
            }
        } catch (Exception e) {
            return;
        }
    }

//______________________________________________________________________________________________________________________________________________________
/*                                                               2 QUÊN MẬT KHẨU                                                                    */
//______________________________________________________________________________________________________________________________________________________
    // Phương thức quên mật khẩu
    private void quenMatKhau() throws MessagingException, UnsupportedEncodingException {
        String taiKhoan = txtC1_TaiKhoan.getText().trim();
        String email = txtC1_Email.getText().trim();
        String maXacNhan = txtC1_MaXacNhan.getText().trim();
        String pass = pwdC1_MatKhauMoi.getText().trim();
        String xacNhanPass = pwdC1_XacNhan.getText().trim();

        //Xét lỗi
        if (taiKhoan.isEmpty() || email.isEmpty()) {
            MessageHelper.alert(this, "KHÔNG ĐƯỢC ĐỂ TRỐNG!", "THÔNG BÁO", linkAnhSai);
            return;
        } else {
            int xetTK = 0;
            for (int i = 0; i < listNhanVien.size(); i++) {
                if (listNhanVien.get(i).getMaNhanVien().equalsIgnoreCase(taiKhoan) && listNhanVien.get(i).getEmail().equalsIgnoreCase(email) && maKhoiPhuc == 0) {
                    MessageHelper.alert(this, "ĐÃ GỬI MÃ XÁC NHẬN ĐẾN EMAIL CỦA BẠN!", "THÔNG BÁO", linkAnhDung);

                    txtC1_TaiKhoan.setEditable(false);
                    txtC1_Email.setEditable(false);
                    txtC1_MaXacNhan.setEditable(true);
                    pwdC1_MatKhauMoi.setEditable(true);
                    pwdC1_XacNhan.setEditable(true);
                    btnC1.setEnabled(true);
                    break;
                }

                if (listNhanVien.get(i).getMaNhanVien().equalsIgnoreCase(taiKhoan)) {
                    xetTK = 1;
                }

                if (i == listNhanVien.size() - 1 && maKhoiPhuc == 0) {
                    if (xetTK == 0) {
                        MessageHelper.alert(this, "TÀI KHOẢN KHÔNG TỒN TẠI!", "THÔNG BÁO", linkAnhSai);
                        return;
                    } else {
                        MessageHelper.alert(this, "EMAIL KHÔI PHỤC KHÔNG HỢP LỆ!", "THÔNG BÁO", linkAnhSai);
                        return;
                    }
                }
            }
        }

        //Gửi mã xác nhận
        //Mail gửi
        if (maKhoiPhuc == 0) {
            String fromEmail = "bonconsoingusi@gmail.com";
            String password = "bonconsoingusi4444";

            //Email người nhận
            String toEmail = email;
            //Tiêu đề
            String subject = "Reset Password";
            int code = (int) Math.floor(((Math.random() * 899999) + 100000));
            String body = "Xin chào " + taiKhoan + "!\n" + "\nChúng tôi nhận được yêu cầu thay đổi mật khẩu của bạn, nếu bạn không yêu cầu thì hãy bỏ qua tin nhắn này."
                    + "\nMã khôi phục của bạn là: " + code;

            //Các thông số kết nối tới mail Sever
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //Tạo đối tượng Session đưa vòa các thông tin xác thực tài khoản email
            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };
            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);

            //Set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(fromEmail, "WOLVESEDU SUPPORT"));
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            //Gửi mail
            Transport.send(msg);

            maKhoiPhuc = code;
            //Kết thúc gửi mã xác nhận
            lblC1_DangGui.setText("OK");
            return;
        }

        //Xét mã xác nhận và thực hiện đổi pass
        try {
            if (maXacNhan.isEmpty() || pass.isEmpty() || xacNhanPass.isEmpty()) {
                MessageHelper.alert(this, "KHÔNG ĐƯỢC ĐỂ TRỐNG!", "THÔNG BÁO", linkAnhSai);
            } else {
                if (Integer.parseInt(maXacNhan) != maKhoiPhuc) {
                    MessageHelper.alert(this, "SAI MÃ XÁC NHẬN!", "THÔNG BÁO", linkAnhSai);
                } else {
                    if (pass.length() < 6 || pass.length() > 16) {
                        MessageHelper.alert(this, "MẬT KHẨU PHẢI TỪ 6-16 KÍ TỰ!", "THÔNG BÁO", linkAnhSai);
                    } else {
                        if (!pass.equals(xacNhanPass)) {
                            MessageHelper.alert(this, "XÁC NHẬN MẬT KHẨU KHÔNG HỢP LỆ!", "THÔNG BÁO", linkAnhSai);
                        } else {
                            NhanVienDAO.updatePassByID(taiKhoan, pass);
                            MessageHelper.alert(this, "LẤY LẠI MẬT KHẨU THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                            clearQuenMatKhau();
                            // Tải lại listNhanVien
                            listNhanVien = NhanVienDAO.selectAll();
                            // Quay lại đăng nhập
                            JFrame_QuenMatKhau.setVisible(false);
                            hienthiFrame(JFrame_DangNhap);
                        }
                    }
                }
            }
        } catch (Exception e) {
            MessageHelper.alert(this, "MÃ XÁC NHẬN PHẢI LÀ SỐ GỒM 6 CHỮ SỐ!", "THÔNG BÁO", linkAnhSai);
        }
    }

    // Phương thức clear Form Quên Mật Khẩu
    private void clearQuenMatKhau() {
        // 
        txtC1_TaiKhoan.setText("");
        txtC1_Email.setText("");
        txtC1_MaXacNhan.setText("");
        pwdC1_MatKhauMoi.setText("");
        pwdC1_XacNhan.setText("");
        // 
        txtC1_TaiKhoan.setEditable(true);
        txtC1_Email.setEditable(true);
        txtC1_MaXacNhan.setEditable(false);
        pwdC1_MatKhauMoi.setEditable(false);
        pwdC1_XacNhan.setEditable(false);
        btnC1.setEnabled(false);

    }

//______________________________________________________________________________________________________________________________________________________
/*                                                                3 ĐỔI MẬT KHẨU                                                                   */
//______________________________________________________________________________________________________________________________________________________
    // Phương thức đổi mật khẩu
    private void doiMatKhau() {
        String matKhauHienTai = pwdD1_MatKhauHienTai.getText().trim();
        String matKhauMoi = pwdD1_MatKhauMoi.getText().trim();
        String xacNhan = pwdD1_XacNhan.getText().trim();

        String nv = lblMaNhanVien.getText().trim();
        String mk = "";

        for (int i = 0; i < listNhanVien.size(); i++) {
            if (nv.equalsIgnoreCase(listNhanVien.get(i).getMaNhanVien())) {
                mk = listNhanVien.get(i).getMatKhau();
                break;
            }
        }

        if (!matKhauHienTai.equalsIgnoreCase(mk)) {
            MessageHelper.alert(this, "MẬT KHẨU HIỆN TẠI KHÔNG CHÍNH XÁC!", "THÔNG BÁO", linkAnhSai);
            return;
        } else {
            if (matKhauMoi.length() < 6 || matKhauMoi.length() > 16) {
                MessageHelper.alert(this, "MẬT KHẨU PHẢI TỪ 6~16 KÍ TỰ!", "THÔNG BÁO", linkAnhSai);
                return;
            } else if (!matKhauMoi.equals(xacNhan)) {
                MessageHelper.alert(this, "XÁC NHẬN MẬT KHẨU KHÔNG CHÍNH XÁC!", "THÔNG BÁO", linkAnhSai);
                return;
            } else {
                NhanVienDAO.updatePassByID(nv, matKhauMoi);
                taiListNhanVien();
                for (int i = 0; i < listNhanVien.size(); i++) {
                    if (listNhanVien.get(i).getMaNhanVien().equalsIgnoreCase(lblMaNhanVien.getText())) {
                        GhiNhoDao.insert(lblMaNhanVien.getText(), listNhanVien.get(i).getMatKhau());
                        break;
                    }
                }
                MessageHelper.alert(this, "ĐỔI MẬT KHẨU THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                pwdB1_MatKhau.setText(matKhauMoi);
                GhiNhoDao.update(lblMaNhanVien.getText().trim(), matKhauMoi);
                clearDoiMatKhau();
                JFrame_DoiMatKhau.setVisible(false);
            }
        }
    }

    // Phương thức clear Form Đổi Mật Khẩu
    private void clearDoiMatKhau() {
        // 
        pwdD1_MatKhauHienTai.setText("");
        pwdD1_MatKhauMoi.setText("");
        pwdD1_XacNhan.setText("");
        // 
        pwdD1_MatKhauHienTai.setEditable(false);
        pwdD1_MatKhauMoi.setEditable(false);
        pwdD1_XacNhan.setEditable(false);
    }

//______________________________________________________________________________________________________________________________________________________
/*                                                                   4 TRANG CHỦ                                                                   */
//______________________________________________________________________________________________________________________________________________________ 
    // Tải các chỉ số trên trang chủ
    private void trangChu() {
        int soNguoiHoc = 0;
        int soHocVien = 0;
        int soKhoaHoc = 0;

        soNguoiHoc = NguoiHocDAO.soNguoiHoc();
        soHocVien = HocVienDAO.soHocVien();
        soKhoaHoc = KhoaHocDAO.soKhoaHoc();

        lbl1So.setText(soNguoiHoc + "");
        lbl2So.setText(soHocVien + "");
        lbl3So.setText(soKhoaHoc + "");
    }

    //  Tải tất cả list
    private void taiListAll() {
        taiListNhanVien();
        taiListChuyenDe();
        taiListKhoaHoc();
        taiListNguoiHoc();
        taiListHocVien_HV("JAB01");
        taiListHocVien_NH("JAB01");
        taiListTK1("nam");
        taiListTK2("JAB01");
        taiListTK3(2020);
        taiListTK4(2020);
        taiCBO();
        taiListGhiNho();
        taiAnhdemo();
        xetNhanVien();
    }

    // Tải lại list sau khi điều chỉnh
    private void taiListLai() {
        trangChu();
        taiListHocVien_HV((String) cboQLHocVien_KhoaHoc.getSelectedItem());
        taiListHocVien_NH((String) cboQLHocVien_KhoaHoc.getSelectedItem());
    }

    // Tải list NhanVien
    private void taiListNhanVien() {
        listNhanVien = NhanVienDAO.selectAll();
        model = (DefaultTableModel) tblQLNhanVien.getModel();
        model.setRowCount(0);
        for (NhanVien nv : listNhanVien) {
            model.addRow(new Object[]{nv.getMaNhanVien(), nv.getHoTen(), nv.getGioiTinh(), toYMD(nv.getNgaySinh()), nv.getSdt(), nv.getEmail(), nv.getDiaChi(), kiemTraAnh(nv.getAnh()), nv.getCCCD(), nv.getChucVu()});
        }
    }

    //
    private void taiListChuyenDe() {
        listChuyenDe = ChuyenDeDAO.selectAll();
        model = (DefaultTableModel) tblQLChuyenDe.getModel();
        model.setRowCount(0);
        for (ChuyenDe cd : listChuyenDe) {
            model.addRow(new Object[]{cd.getMaChuyenDe(), cd.getTenChuyenDe(), (long) cd.getHocPhi(), cd.getThoiLuong(), kiemTraAnh(cd.getAnh()), cd.getMoTa()});
        }
        loadCboKhoaHoc();
    }

    // Phương thức chuyển ngày yyyy-MM-dd
    private String toYMD(String ngay) {
        return DateHelper.toString(DateHelper.toDate(ngay, "dd-MM-yyyy"), "yyyy-MM-dd");
    }

    // 
    private void taiListKhoaHoc() {
        listKhoaHoc = KhoaHocDAO.selectAll();
        model = (DefaultTableModel) tblQLKhoaHoc.getModel();
        model.setRowCount(0);
        for (KhoaHoc kh : listKhoaHoc) {
            model.addRow(new Object[]{kh.getMaKhoaHoc(), kh.getMaChuyenDe(), (long) kh.getHocPhi(), kh.getThoiLuong(), toYMD(kh.getNgayKhaiGiang()), kh.getNguoiTao(), toYMD(kh.getNgayTao()), kh.getGhiChu()});
        }
    }

    // 
    private void taiListNguoiHoc() {
        listNguoiHoc = NguoiHocDAO.selectAll();
        model = (DefaultTableModel) tblQLNguoiHoc.getModel();
        model.setRowCount(0);
        for (NguoiHoc nh : listNguoiHoc) {
            model.addRow(new Object[]{nh.getMaNguoiHoc(), nh.getHoTen(), nh.getGioiTinh(), toYMD(nh.getNgaySinh()), nh.getSdt(), nh.getEmail(), nh.getDiaChi(), "Đã có ảnh", nh.getGhiChu(), nh.getNguoiTao(), toYMD(nh.getNgayDangKy())});
        }
    }

    // Tải list học viên - học viên
    private void taiListHocVien_HV(String maKhoaHoc) {
        listHocVien_HV = HocVienDAO.selectHV_HV(maKhoaHoc);
        model = (DefaultTableModel) tblQLHocVien_HocVien.getModel();
        model.setRowCount(0);
        for (Object[] hv : listHocVien_HV) {
            model.addRow(new Object[]{hv[0], hv[1], hv[2], hv[3]});
        }
    }

    // Tải list học viên - người học
    private void taiListHocVien_NH(String maKhoaHoc) {
        listHocVien_NH = HocVienDAO.selectHV_NH(maKhoaHoc);
        model = (DefaultTableModel) tblQLHocVien_NguoiHoc.getModel();
        model.setRowCount(0);
        for (NguoiHoc nh : listHocVien_NH) {
            model.addRow(new Object[]{nh.getMaNguoiHoc(), nh.getHoTen(), nh.getGioiTinh(), toYMD(nh.getNgaySinh()), nh.getSdt(), nh.getEmail(), nh.getDiaChi()});
        }
    }

    // Tải list thống kê 1
    private void taiListTK1(String thoiGian) {
        listTK1 = ThongKeDAO.tk1(thoiGian);
        model = (DefaultTableModel) tblTKNguoiHoc.getModel();
        model.setRowCount(0);
        for (Object[] tk1 : listTK1) {
            model.addRow(new Object[]{tk1[0], tk1[1], toYMD(String.valueOf(tk1[2])), toYMD(String.valueOf(tk1[3]))});
        }
    }

    // Tải list thống kê 2
    private void taiListTK2(String maKhoaHoc) {
        listTK2 = ThongKeDAO.tk2(maKhoaHoc);
        model = (DefaultTableModel) tblTKDiem_KhoaHoc.getModel();
        model.setRowCount(0);
        for (Object[] tk2 : listTK2) {
            model.addRow(new Object[]{tk2[0], tk2[1], tk2[2], tk2[3]});
        }
    }

    // Tải list thống kê 3
    private void taiListTK3(int nam) {
        listTK3 = ThongKeDAO.tk3(nam);
        model = (DefaultTableModel) tblTKDiem_ChuyenDe.getModel();
        model.setRowCount(0);
        for (Object[] tk3 : listTK3) {
            model.addRow(new Object[]{tk3[0], tk3[1], tk3[2], tk3[3], tk3[4]});
        }
    }

    // Tải list thống kê 4
    private void taiListTK4(int nam) {
        listTK4 = ThongKeDAO.tk4(nam);
        model = (DefaultTableModel) tblTKDoanhThu_ChuyenDe.getModel();
        model.setRowCount(0);
        for (Object[] tk4 : listTK4) {
            model.addRow(new Object[]{tk4[0], tk4[1], tk4[2], tk4[3], tk4[4], tk4[5], tk4[6]});
        }
    }

    // Tải các combobox thống kê và quản lý học viên
    private void taiCBO() {
        List<Object[]> list = new ArrayList<>();
        cboQLHocVien_ChuyenDe.removeAllItems();
        cboQLHocVien_KhoaHoc.removeAllItems();
        cbo7B_ChuyenDe.removeAllItems();
        cbo7B_KhoaHoc.removeAllItems();
        cbo7C_Nam.removeAllItems();
        cbo7D_Nam.removeAllItems();

        listTK2_CD = ThongKeDAO.cboChuyenDe();
        for (Object[] cbo : listTK2_CD) {
            cboQLHocVien_ChuyenDe.addItem((String) cbo[0]);
            cbo7B_ChuyenDe.addItem((String) cbo[0]);
        }

        list.clear();
        list = ThongKeDAO.cboKhoaHoc("");
        for (Object[] cbo : list) {
            cboQLHocVien_KhoaHoc.addItem((String) cbo[0]);
            cbo7B_KhoaHoc.addItem((String) cbo[0]);
        }

        list.clear();
        list = ThongKeDAO.cboNam();
        for (Object[] cbo : list) {
            cbo7C_Nam.addItem(String.valueOf(cbo[0]));
            cbo7D_Nam.addItem(String.valueOf(cbo[0]));
        }
    }

    // Tải đối tượng đầu tiền
    private void taiDoiTuong1() {
        try {
            if (listChuyenDe.size() > 0) {
                writeFormQLChuyenDe(0);
            }
            if (listKhoaHoc.size() > 0) {
                writeFormQLKhoaHoc(0);
            }
            if (listNhanVien.size() > 0) {
                writeForm(listNhanVien.get(0));
            }
        } catch (Exception e) {
            return;
        }
    }

//______________________________________________________________________________________________________________________________________________________
/*                                                           5 QUẢN LÝ CHUYÊN ĐỀ                                                                    */
//______________________________________________________________________________________________________________________________________________________
    // Viết dữ liệu lên form cập nhật chuyên đề 
    private void writeFormQLChuyenDe(int x) {
        txt2A_MaChuyenDe.setText(tblQLChuyenDe.getValueAt(x, 0).toString());
        txt2A_TenChuyenDe.setText(tblQLChuyenDe.getValueAt(x, 1).toString());
        txt2A_HocPhi.setText(Long.valueOf(String.valueOf(tblQLChuyenDe.getValueAt(x, 2))) + "");
        txt2A_ThoiLuong.setText(tblQLChuyenDe.getValueAt(x, 3).toString());
        lbl2A_AnhDaiDien.setIcon(new ImageIcon(ImageHelper.anhChuan(listChuyenDe.get(x).getAnh(), lbl2A_AnhDaiDien.getWidth(), lbl2A_AnhDaiDien.getHeight())));
        txt2A_MoTa.setText(tblQLChuyenDe.getValueAt(x, 5).toString());
        tblQLChuyenDe.setRowSelectionInterval(x, x);

    }

    // 
    private void setFormChuyenDe(ChuyenDe cd) {
        txt2A_MaChuyenDe.setText(cd.getMaChuyenDe());
        txt2A_TenChuyenDe.setText(cd.getTenChuyenDe());
        txt2A_HocPhi.setText((long) cd.getHocPhi() + "");
        txt2A_ThoiLuong.setText(String.valueOf(cd.getThoiLuong()));
        if (cd.getAnh() != null) {
            lbl2A_AnhDaiDien.setIcon(new ImageIcon(ImageHelper.anhChuan(cd.getAnh(), lbl2A_AnhDaiDien.getWidth(), lbl2A_AnhDaiDien.getHeight())));
        } else {
            lbl2A_AnhDaiDien.setIcon(null);
        }
        txt2A_MoTa.setText(cd.getMoTa());
    }

    // đọc dữ liệu từ chuyên đề
    ChuyenDe getFormChuyende() {
        ChuyenDe cd = new ChuyenDe();
        String maChuyenDe = txt2A_MaChuyenDe.getText().trim();//lấy giá trị mã chuyên đề
        String tenChuyende = txt2A_TenChuyenDe.getText().trim();//lấy giá trị tên chuyên dề
        String moTa = txt2A_MoTa.getText().trim();//lấy giá trị mô tả chuyên đề
        String hocPhi = txt2A_HocPhi.getText().trim();//lấy giá trị học phí chuyên đề
        String thoiLuong = txt2A_ThoiLuong.getText().trim();//lấy giá trị thời lượng chuyên đề  
        //Nếu 1 trong các dữ liệu bị rỗng
        if (maChuyenDe.isEmpty() || tenChuyende.isEmpty() || hocPhi.isEmpty() || thoiLuong.isEmpty()) {
            if (maChuyenDe.isEmpty()) {//Mã chuyên đề bị rỗng
                MessageHelper.alert(this, "MÃ CHUYÊN ĐỀ BỊ TRỐNG!!", "THÔNG BÁO", linkAnhSai);
            } else if (tenChuyende.isEmpty()) {//tên chuyên đề bị rỗng
                MessageHelper.alert(this, "TÊN CHUYÊN ĐỀ BỊ TRỐNG!", "THÔNG BÁO", linkAnhSai);
            } else if (hocPhi.isEmpty()) {//học phí bị rỗng
                MessageHelper.alert(this, "HỌC PHÍ BỊ TRỐNG!", "THÔNG BÁO", linkAnhSai);
            } else if (thoiLuong.isEmpty()) {// thời lượng bị rỗng
                MessageHelper.alert(this, "THỜI LƯỢNG BỊ TRỐNG!", "THÔNG BÁO", linkAnhSai);
            }
            return null;//nếu một trong các giá trị bị rỗng trả về null
        } else {
            // PHÂN TÍCH DỮ LIỆU GETFORMCHUYENDE
            try {
                //Mã chuyên dề
                cd.setMaChuyenDe(maChuyenDe);
                //Tên chuyên đề
                cd.setTenChuyenDe(tenChuyende);
                //học phí
                try {
                    if (Float.valueOf(hocPhi) > 0) {
                        cd.setHocPhi(Float.valueOf(hocPhi));
                    } else {
                        MessageHelper.alert(this, "HỌC PHÍ PHẢI LỚN HƠN 0!", "THÔNG BÁO", linkAnhSai);
                        return null;
                    }

                } catch (Exception e) {
                    MessageHelper.alert(this, "HỌC PHÍ PHẢI LÀ SỐ !", "THÔNG BÁO", linkAnhSai);
                    return null;
                }
                //thời lượng
                try {
                    if (Integer.valueOf(thoiLuong) > 0) {
                        cd.setThoiLuong(Integer.valueOf(thoiLuong));
                    } else {
                        MessageHelper.alert(this, "THỜI LƯỢNG PHẢI LỚN HƠN 0!", "THÔNG BÁO", linkAnhSai);
                        return null;
                    }

                } catch (Exception e) {
                    MessageHelper.alert(this, "THỜI LƯỢNG PHẢI LÀ SỐ NGUYÊN!", "THÔNG BÁO", linkAnhSai);
                    return null;
                }
                //Mô tả
                if (moTa.equals("")) {//nếu mô tả rỗng thì sẽ hiện lên là trống
                    moTa = "Trống";
                }
                cd.setMoTa(moTa);

            } catch (Exception e) {
                System.out.println("Lỗi Phân tích dữ liệu getFormChuyenDe");
            }
            return cd;
        }
    }

    //Kiêm tra tồn tại mã chuyên dề (DTSD: CD, KH)
    private boolean kiemTraMaChuyenDe(String x) {
        boolean kt = true;
        for (int i = 0; i < listChuyenDe.size(); i++) {
            if (x.equalsIgnoreCase(listChuyenDe.get(i).getMaChuyenDe())) {
                kt = false;
                break;
            }
        }
        return kt;
    }

    // Xét hơn mã
    private boolean xetMa10KyTu(String ma) {
        if (ma.length() > 10) {
            return false;
        }
        return true;
    }

    //Chức Năng Thêm Chuyên Đề
private void themChuyenDe() {
        if (tblQLChuyenDe.getSelectedRow() >= 0) {
            MessageHelper.alert(this, "KHÔNG THỂ THÊM CHUYÊN ĐỀ KHI ĐANG CHỌN CHUYÊN ĐỀ KHÁC!", "THÔNG BÁO", linkAnhSai);
        } else {
            ChuyenDe cd = getFormChuyende();
            if (cd == null) {
            } else {
                //Kiểm tra tồn tại của mã chuyên đề trong cơ sở dữ liệu
                if (kiemTraMaChuyenDe(cd.getMaChuyenDe()) == true) {//Mã chuyên dề chưa có trong cơ sở dữ liệu
                    //TIẾN HÀNH THÊM
                    if (linkAnhChuyenDe.equals("")) {//không chọn ảnh cho chuyên đề
                        linkAnhChuyenDe = "image/chuyende/cd00.png";
                        ChuyenDeDAO.insert(cd, linkAnhChuyenDe);
                    } else {// chọn ảnh cho chuyên đề
                        ChuyenDeDAO.insert(cd, linkAnhChuyenDe);
                    }
                    //tiến hành thông báo và tải reset lại list và form chuyên đề
                    lamMoi_CapNhatChuyenDe();
                    taiListChuyenDe();
                    MessageHelper.alert(this, "THÊM THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);

                } else {//mã chuyên đề đã có trong cơ sở dữ liệu
                    MessageHelper.alert(this, "MÃ CHUYÊN ĐỀ ĐÃ TỒN TẠI!", "THÔNG BÁO", linkAnhSai);
                }
            }
        }
    }

    //Chức năng Sửa Chuyên Đề
    private void suaChuyenDe() {
        ChuyenDe cd = getFormChuyende();
        if (cd == null) {

        } else {
            String maChuyenDe = (String) tblQLChuyenDe.getValueAt(this.row, 0);
            if (linkAnhChuyenDe.equals("")) {//người dùng không thay đổi ảnh
                ChuyenDeDAO.update(cd, maChuyenDe);
            } else {//người dùng thay đổi ảnh mới
                ChuyenDeDAO.update(cd, maChuyenDe, linkAnhChuyenDe);
            }
            taiListChuyenDe();
            taiListKhoaHoc();
            taiListLai();
            lamMoi_CapNhatChuyenDe();
            MessageHelper.alert(this, "CẬP NHẬT THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
        }
    }

    // Chức năng xóa chuyên đề
    private boolean kiemTraTonTaiChuyenDeTrongKhoaHoc(String x) { //x mã chuyên đề của list chuyên đề
        boolean kiemTra = true;
        String CDKH = "";//mã chuyên đề của khóa học
        for (int i = 0; i < listKhoaHoc.size(); i++) {
            CDKH = listKhoaHoc.get(i).getMaChuyenDe();
            if (CDKH.equals(x)) {
                kiemTra = false;
                break;
            }
        }
        return kiemTra;// chuyên đề chứa khóa học: false
    }

    // 
    private void xoaChuyenDe() {
        boolean chon = true;// xác nhận xóa
        int x = 0;// 0: không có khóa học trong chuyên đề
        // 1: có khóa học trong chuyên đề
        String maChuyenDe = (String) tblQLChuyenDe.getValueAt(this.row, 0);
        if (kiemTraTonTaiChuyenDeTrongKhoaHoc(maChuyenDe) == true) {//không có khóa học trong chuyên đề
            chon = MessageHelper.confirm(this, "BẠN THẬT SỰ MUỐN XÓA?", "XÁC NHẬN", linkAnhHoi);
            x = 0;
        } else {//có khóa học trong chuyên đề
            chon = MessageHelper.confirm(this, "ĐANG CÓ KHÓA HỌC DIỄN RA VỚI CHUYÊN ĐỀ NÀY ! \nTOÀN BỘ DỮ LIỆU KHÓA HỌC SẼ BỊ XÓA \nBẠN THẬT SỰ MUỐN XÓA?", "XÁC NHẬN", linkAnhHoi);
            x = 1;
        }
        if (chon == true) {// nếu xác nhận xóa chuyên đề
            if (x == 1) {// trường hợp có khóa học trong chuyên đề
                //XÓA KHÓA HỌC CỦA CHUYÊN ĐỀ
                KhoaHocDAO.deletebymaCD(maChuyenDe);
                taiListKhoaHoc();
                lamMoi_CapNhatKhoaHoc();
            }
            //XÓA CHUYÊN ĐỀ
            ChuyenDe cd = getFormChuyende();
            ChuyenDeDAO.delete(maChuyenDe);
            taiListChuyenDe();
            lamMoi_CapNhatChuyenDe();
            taiListKhoaHoc();
            taiListLai();
            taiCBO();
            MessageHelper.alert(this, "XÓA THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
        } else {
            // System.out.println("Không xóa ...");
        }
    }

    //Lấy mã để load lên form cập nhật chuyên đề
    private void editTblQlChuyenDe() {
        //lấy mã
        String maChuyenDe = (String) tblQLChuyenDe.getValueAt(this.row, 0);
        ChuyenDe cd = ChuyenDeDAO.selectById(maChuyenDe);
        //load mã
        this.setFormChuyenDe(cd);
    }

    //Nút Trước
    private void Truoc() {
        this.row = 0;
        writeFormQLChuyenDe(row);
    }

    //Nút Sau
    private void Sau() {
        this.row = tblQLChuyenDe.getRowCount() - 1;
        writeFormQLChuyenDe(row);
    }

    //Nút Về Sau
    private void veSau() {
        if (this.row < tblQLChuyenDe.getRowCount() - 1) {
            this.row++;
            writeFormQLChuyenDe(row);
        } else {
            Truoc();
        }
    }

    //Nút Về trước
    private void veTruoc() {
        if (this.row > 0) {
            this.row--;
            writeFormQLChuyenDe(row);
        } else {
            Sau();
        }
    }

    //Chọn hình chuyên đề
    private String chonHinhChuyenDe() {
        //Thư viện hộp thoại Folder
        JFileChooser chooser = new JFileChooser("image/chuyende/");
        chooser.showOpenDialog(null);//hộp thoại save
        //  file đã chọn
        File f = chooser.getSelectedFile(); // f : file đã chọn
        //Kiểm tra file vừa chọn
        if (f != null) {// chọn
            linkAnhChuyenDe = f.getPath();//link ảnh vừa chọn
            //tạo ảnh
            ImageIcon anhChuyendeIcon = new ImageIcon(new ImageIcon(linkAnhChuyenDe).getImage()
                    .getScaledInstance(lbl2A_AnhDaiDien.getWidth(), lbl2A_AnhDaiDien.getHeight(), Image.SCALE_SMOOTH));
            //load ảnh lên label
            lbl2A_AnhDaiDien.setIcon(anhChuyendeIcon);//lay hinh nah dua vao lbl
        } else {//không chọn
            linkAnhChuyenDe = "";
        }
        //System.out.println(linkAnhChuyenDe);
        return linkAnhChuyenDe;
    }

    //Làm mới form đăng nhập 
    private void lamMoi_CapNhatChuyenDe() {
        txt2A_MaChuyenDe.setText("");
        txt2A_TenChuyenDe.setText("");
        txt2A_HocPhi.setText("");
        txt2A_ThoiLuong.setText("");
        lbl2A_AnhDaiDien.setIcon(new ImageIcon());
        lbl2A_AnhDaiDien.setText("Ảnh");
        txt2A_MoTa.setText("");
        //Bỏ chọn bảng tblQLChuyenDe
        tblQLChuyenDe.clearSelection();

        txt2A_MaChuyenDe.setEnabled(true);
    }

    //Tìm kiếm chuyên đề
    private void timKiemChuyenDe() {
        String tuKhoa = txt2B_TimKiem.getText().trim(); // từ khóa tìm kiếm
        //truy vấn từ khóa
        listChuyenDe = ChuyenDeDAO.selectAll(tuKhoa);
        //fill lên bảng
        model = (DefaultTableModel) tblQLChuyenDe.getModel();
        model.setRowCount(0);
        for (ChuyenDe cd : listChuyenDe) {
            model.addRow(new Object[]{cd.getMaChuyenDe(), cd.getTenChuyenDe(), cd.getHocPhi(), cd.getThoiLuong(), kiemTraAnh(cd.getAnh()), cd.getMoTa()});
        }
    }

//______________________________________________________________________________________________________________________________________________________
/*                                                           6 QUẢN LÝ KHÓA  HỌC                                                                    */
//______________________________________________________________________________________________________________________________________________________
    //Viết dữ liệu lên form cập nhật khóa học
    private void writeFormQLKhoaHoc(int x) {
        txt3A_MaChuyenDe.setText(listKhoaHoc.get(x).getMaChuyenDe());
        kiemTraTenChuyenDe(listKhoaHoc.get(x).getMaChuyenDe());
        txt3A_HocPhi.setText(String.valueOf((long) listKhoaHoc.get(x).getHocPhi()));
        txt3A_ThoiLuong.setText(String.valueOf(listKhoaHoc.get(x).getThoiLuong()));
        txt3A_GhiChu.setText(listKhoaHoc.get(x).getGhiChu());
        txt3A_MaKhoaHoc.setText(listKhoaHoc.get(x).getMaKhoaHoc());
        txt3A_NgayKhaiGiang.setText(listKhoaHoc.get(x).getNgayKhaiGiang());
        txt3A_NgayTao.setText(listKhoaHoc.get(x).getNgayTao());
        tblQLKhoaHoc.setRowSelectionInterval(x, x);

    }

    //Điền dữ liệu lên form 
    private void setFormKhoaHoc(KhoaHoc kh) {
        txt3A_MaChuyenDe.setText(kh.getMaChuyenDe());
        kiemTraTenChuyenDe(kh.getMaChuyenDe());//chọn combobox
        txt3A_HocPhi.setText(String.valueOf(kh.getHocPhi()));
        txt3A_ThoiLuong.setText(String.valueOf(kh.getThoiLuong()));
        txt3A_GhiChu.setText(kh.getGhiChu());
        txt3A_MaKhoaHoc.setText(kh.getMaKhoaHoc());
        txt3A_NgayKhaiGiang.setText(kh.getNgayKhaiGiang());
        txt3A_NgayTao.setText(kh.getNgayTao());
    }

    //
    //đọc dữ liệu từ form khóa học
    KhoaHoc getFormKhoaHoc() {
        KhoaHoc kh = new KhoaHoc();
        //Lấy dữ liệu từ textfeild của form cập nhật chuyên đề
        String maKhoaHoc = txt3A_MaKhoaHoc.getText().trim();
        String maChuyenDe = txt3A_MaChuyenDe.getText().trim();
        String thoiLuong = txt3A_ThoiLuong.getText().trim();
        String hocPhi = txt3A_HocPhi.getText().trim();
        String ngayKhaiGiang = txt3A_NgayKhaiGiang.getText().trim();
        ngayKhaiGiang = doiKieuNgay(ngayKhaiGiang);
        String nguoiTao = lblMaNhanVien.getText().trim();
        String ngayTao = txt3A_NgayTao.getText().trim();
        ngayTao = doiKieuNgay(ngayTao);
        String ghiChu = txt3A_GhiChu.getText().trim();
        //Nếu 1 trong các dữ liệu bị rỗng
        if (maChuyenDe.isEmpty() || maKhoaHoc.isEmpty() || thoiLuong.isEmpty() || hocPhi.isEmpty() || ngayKhaiGiang.isEmpty() || ngayTao.isEmpty()) {
            if (maChuyenDe.isEmpty()) {
                MessageHelper.alert(this, "VUI LÒNG CHỌN CHUYÊN ĐỀ HOẶC NHẬP MÃ CHUYÊN ĐỀ!", "THÔNG BÁO", linkAnhSai);
            } else if (maKhoaHoc.isEmpty()) {
                MessageHelper.alert(this, "MÃ KHÓA HỌC BỊ TRỐNG!", "THÔNG BÁO", linkAnhSai);
            } else if (thoiLuong.isEmpty()) {
                MessageHelper.alert(this, "THÒI LƯỢNG BỊ TRỐNG!", "THÔNG BÁO", linkAnhSai);
            } else if (hocPhi.isEmpty()) {
                MessageHelper.alert(this, "HỌC PHÍ BỊ TRỐNG!", "THÔNG BÁO", linkAnhSai);
            } else if (ngayKhaiGiang.isEmpty()) {
                MessageHelper.alert(this, "NGÀY KHAI GIẢNG BỊ TRỐNG!", "THÔNG BÁO", linkAnhSai);
            } else if (ngayTao.isEmpty()) {
                MessageHelper.alert(this, "NGÀY TẠO BỊ TRỐNG!", "THÔNG BÁO", linkAnhSai);
            }
            return null;
        } else {
            try {
                // mã khóa học
                kh.setMaKhoaHoc(maKhoaHoc);

                //mã chuyên đề
                if (kiemTraMaChuyenDe(maChuyenDe) == false) {//kiểm tra chuyên đề hợp lệ 
                    kh.setMaChuyenDe(maChuyenDe);
                } else {// chuyên đề ko hợp lệ 
                    MessageHelper.alert(this, "MÃ CHUYÊN ĐỀ KHÔNG TỒN TẠI!", "THÔNG BÁO", linkAnhSai);
                    return null;
                }
                //thời lượng
                try {
                    if (Integer.valueOf(thoiLuong) > 0) {//thời lượng lớn hơn 0
                        kh.setThoiLuong(Integer.parseInt(thoiLuong));
                    } else {//thời lượng bé hơn 0
                        MessageHelper.alert(this, "THỜI LƯỢNG PHẢI LỚN HƠN 0!", "THÔNG BÁO", linkAnhSai);
                        return null;
                    }
                } catch (Exception e) {//sai định dạng
                    MessageHelper.alert(this, "THỜI LƯỢNG PHẢI LÀ SỐ NGUYÊN!", "THÔNG BÁO", linkAnhSai);
                    return null;
                }

                //học phí
                try {
                    if (Float.valueOf(hocPhi) > 0) {//học phí lớn hơn 0
                        kh.setHocPhi(Float.parseFloat(hocPhi));
                    } else {// học phí nhỏ hơn 0
                        MessageHelper.alert(this, "HỌC PHÍ PHẢI LỚN HƠN 0!", "THÔNG BÁO", linkAnhSai);
                        return null;
                    }
                } catch (Exception e) {//sai định dạng học phí
                    MessageHelper.alert(this, "HỌC PHÍ PHẢI LÀ SỐ!", "THÔNG BÁO", linkAnhSai);
                    return null;
                }
                //ngày khai giảng
                if (!kiemTraNgay(ngayKhaiGiang)) {
                    MessageHelper.error(this, "Ngày khai giảng không đúng định dạng (01-01-2021)", linkAnhSai);
                    txt3A_NgayKhaiGiang.requestFocus();
                    return null;
                } else {
                    kh.setNgayKhaiGiang(ngayKhaiGiang);
                }

                //người tạo
                kh.setNguoiTao(nguoiTao);
                //ngày tạo
                if (!kiemTraNgay(ngayTao)) {
                    MessageHelper.error(this, "Ngày tạo không đúng định dạng (01-01-2021)", linkAnhSai);
                    txt3A_NgayTao.requestFocus();
                    return null;
                } else {
                    kh.setNgayTao(ngayTao);
                }

                // ngày khai giảng sau ngày tạo
                if (!xetNgay(ngayKhaiGiang, "lon", ngayTao)) {
                    MessageHelper.error(this, "Ngày khai giảng phải sau ngày tạo", linkAnhSai);
                    txt3A_NgayKhaiGiang.requestFocus();
                    return null;
                }

//                // 
//                if (!xetNgay(ngayTao, "lon", "now")) {
//                    MessageHelper.error(this, "Ngày tạo không được trước ngày hôm nay!", linkAnhSai);
//                    txt3A_NgayTao.requestFocus();
//                    return null;
//                }
                //ghi chú
                if (ghiChu.equals("")) {
                    ghiChu = "Trống";
                }
                kh.setGhiChu(ghiChu);
            } catch (Exception e) {
                System.out.println("Lỗi tra cứu dữ liệu getFormKhoaHoc");
            }
            return kh;// null khi có một dữ liệu không hợp lệ 
        }

    }

    //Chọn vị trí cbo tên chuyên đề của khóa học theo mã khóa học
    private void kiemTraTenChuyenDe(String x) {//mã chuyên đề
        for (int i = 0; i < listChuyenDe.size(); i++) {
            String tenCD = (String) cbo3A_TenChuyenDe.getSelectedItem();
            if (listChuyenDe.get(i).getMaChuyenDe().equals(x)) {
                cbo3A_TenChuyenDe.setSelectedIndex(i);
                break;
            }
        }
    }

    //Thêm Khóa học
    //Kiểm tra mã khoa học trên form với list Khóa học
    private boolean kiemTraMaKhoaHoc(String x) { // x: mã khóa học mới 
        boolean kt = true;
        //kiểm tra
        for (int i = 0; i < listKhoaHoc.size(); i++) {
            if (x.equalsIgnoreCase(listKhoaHoc.get(i).getMaKhoaHoc())) {
                kt = false;
                break;
            }
        }
        return kt;//nếu mã mới tồn tại : false.
    }

    private void themKhoaHoc() {
        KhoaHoc kh = getFormKhoaHoc();
        if (tblQLKhoaHoc.getSelectedRow() >= 0) {
            MessageHelper.alert(this, "KHÔNG THỂ THÊM KHÓA HỌC KHI ĐANG CHỌN KHÓA HỌC KHÁC!", "THÔNG BÁO", linkAnhSai);
        } else {
            if (kh == null) {// dữ liệu không hợp lệ
//            MessageHelper.alert(this, "THÊM THẤT BẠI", "THÔNG BÁO", linkAnhSai);
            } else {// dữ liệu hợp lệ
                if (kiemTraMaKhoaHoc(kh.getMaKhoaHoc()) == true) {//mã khóa học chưa có trong listKhoaHoc
                    KhoaHocDAO.insert(kh);
                    taiListKhoaHoc();
                    lamMoi_CapNhatKhoaHoc();
                    trangChu();
                    taiListLai();
                    taiCBO();
                    MessageHelper.alert(this, "THÊM THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                } else {//mã đã tồn tại
                    MessageHelper.alert(this, "MÃ KHÓA HỌC ĐÃ TỒN TẠI", "THÔNG BÁO", linkAnhSai);
                }
            }
        }
    }

    //Chức năng Sửa khóa học
    private void suaKhoaHoc() {
        KhoaHoc kh = getFormKhoaHoc();
        if (kh == null) {// dữ liệu không hợp lệ 
//            MessageHelper.alert(this, "CẬP NHẬT THẤT BẠI", "THÔNG BÁO", linkAnhSai);
        } else {// dữ liệu hợp lệ
            String maKhoaHoc = (String) tblQLKhoaHoc.getValueAt(this.row, 0);
            KhoaHocDAO.update(kh, maKhoaHoc);
            taiListKhoaHoc();
            lamMoi_CapNhatKhoaHoc();
            trangChu();
            MessageHelper.alert(this, "CẬP NHẬT THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
        }
    }

    //Xóa khóa học
    //Kiểm tra tồn tại khóa học của học viên khóa học 
    private boolean kiemTraTonTaiMaKH_HocVien(String x) {//x: mã khóa học của listKhoaHoc
        boolean kt = true;
        listHocVien_HV2 = HocVienDAO.selectAll();
        for (int i = 0; i < listHocVien_HV2.size(); i++) {
            if (x.equals(listHocVien_HV2.get(i).getMaKhoaHoc())) {
                kt = false;
                break;
            }
        }
        return kt;
    }

    private void xoaKhoaHoc() {
        boolean chon = MessageHelper.confirm(this, "BẠN THẬT SỰ MUỐN XÓA?", "XÁC NHẬN", linkAnhHoi);
        if (chon == true) {// xác nhận
            String maKhoaHoc = (String) tblQLKhoaHoc.getValueAt(this.row, 0);
            String maKhoaHocHV = maKhoaHoc;
            if (kiemTraTonTaiMaKH_HocVien(maKhoaHocHV) == false) {// khóa học đang chứa học viên
                boolean chonHV = MessageHelper.confirm(this, "ĐANG TỒN TẠI HỌC VIÊN TRONG KHÓA HỌC NÀY!\nBẠN THẬT SỰ MUỐN XÓA?", "XÁC NHẬN", linkAnhHoi);
                if (chonHV == true) {//xác nhận xóa
                    HocVienDAO.deleteHV(maKhoaHocHV);
                    KhoaHocDAO.delete(maKhoaHoc);
                    taiListKhoaHoc();
                    taiListLai();
                    taiCBO();
                    lamMoi_CapNhatKhoaHoc();
                    MessageHelper.alert(this, "XÓA THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                }
            } else {//Xóa khóa học không học viên
                KhoaHocDAO.delete(maKhoaHoc);
                taiListKhoaHoc();
                lamMoi_CapNhatKhoaHoc();
                taiListLai();
                taiCBO();
                MessageHelper.alert(this, "XÓA THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
            }
        } else {
            // System.out.println("khong Xoa");
        }
    }

    //fill lên form 
    private void editTblQlKhoaHoc() {
        //lấy mã
        String maKhoaHoc = (String) tblQLKhoaHoc.getValueAt(this.row, 0);
        KhoaHoc kh = KhoaHocDAO.selectById(maKhoaHoc);
        //load mã
        this.setFormKhoaHoc(kh);
    }

    //Nút Trước
    private void TruocKH() {
        this.row = 0;
        writeFormQLKhoaHoc(row);
    }

    //Nút sau
    private void SauKH() {
        this.row = tblQLKhoaHoc.getRowCount() - 1;
        writeFormQLKhoaHoc(row);
    }

    //Nút về sau
    private void veSauKH() {
        if (this.row < tblQLKhoaHoc.getRowCount() - 1) {
            this.row++;
            writeFormQLKhoaHoc(row);
        } else {
            TruocKH();
        }
    }

    //Nút Về trước
    private void veTruocKH() {
        if (this.row > 0) {
            this.row--;
            writeFormQLKhoaHoc(row);
        } else {
            SauKH();
        }
    }

    //Làm mới cập nhật khóa học
    private void lamMoi_CapNhatKhoaHoc() {
        loadCboKhoaHoc();
        txt3A_MaChuyenDe.setText("");
        txt3A_HocPhi.setText("");
        txt3A_ThoiLuong.setText("");
        txt3A_GhiChu.setText("");
        txt3A_NgayKhaiGiang.setText("");
        //Bỏ chọn bảng tblQLChuyenDe
        tblQLKhoaHoc.clearSelection();
        cbo3A_TenChuyenDe.setSelectedIndex(0);

        txt3A_MaKhoaHoc.setEnabled(true);
        txt3A_NgayTao.setText(String.valueOf(localDate.getDayOfMonth()) + "-" + String.valueOf(localDate.getMonth().getValue()) + "-" + String.valueOf(localDate.getYear()));
    }

    //Tai combobox ten chuyen de
    private void loadCboKhoaHoc() {
        cbo3A_TenChuyenDe.removeAllItems();
        for (int i = 0; i < listChuyenDe.size(); i++) {
            cbo3A_TenChuyenDe.addItem(listChuyenDe.get(i).getTenChuyenDe());
        }
    }

    //Action perfom cbo tên chuyên đề
    private void keyTenCD_KhoaHoc() {
        String tenCD = ((String) cbo3A_TenChuyenDe.getSelectedItem());
        //System.out.println(tenCD);
        if (tenCD != null) {
            for (int i = 0; i < listChuyenDe.size(); i++) {
                if (tenCD.equalsIgnoreCase(listChuyenDe.get(i).getTenChuyenDe())) {
                    txt3A_MaChuyenDe.setText(listChuyenDe.get(i).getMaChuyenDe());
                    txt3A_MaKhoaHoc.setText(listChuyenDe.get(i).getMaChuyenDe());
                    txt3A_HocPhi.setText(listChuyenDe.get(i).getHocPhi() + "");
                    txt3A_ThoiLuong.setText(listChuyenDe.get(i).getThoiLuong() + "");
                }
            }
        }
    }

    //keyrelease textfeild mã chuyên đề khóa học
    private void keyMaChuyenDe_KhoaHoc() {
        String x = txt3A_MaChuyenDe.getText();
        kiemTraTenChuyenDe(x);
    }

    //tìm kiếm khóa học
    private void timKiemKhoaHoc() {
        String tuKhoa = txt3B_TimKiem.getText().trim();// từ khóa tìm kiếm
        //truy vấn
        listKhoaHoc = KhoaHocDAO.selectAll(tuKhoa);
        //fill lên bảng
        model = (DefaultTableModel) tblQLKhoaHoc.getModel();
        model.setRowCount(0);
        for (KhoaHoc kh : listKhoaHoc) {
            model.addRow(new Object[]{kh.getMaKhoaHoc(), kh.getMaChuyenDe(), kh.getThoiLuong(), kh.getHocPhi(), kh.getNgayKhaiGiang(), kh.getNguoiTao(), kh.getNgayTao(), kh.getGhiChu()});
        }
    }

//______________________________________________________________________________________________________________________________________________________
/*                                                           7 QUẢN LÝ NGƯỜI HỌC                                                                    */
//______________________________________________________________________________________________________________________________________________________
    int index = -1; // hàng được chọn
    String check1 = "[0-9 ]+";
    String check2 = "0[35789]{1}[0-9]{8}";
    String check3 = "[A-z]+[A-z0-9]*((@gmail.com)|(@GMAIL.COM))";
    String check4 = "[a-z]{2,}pc[0-9]{5}((@fpt.edu.vn)|(@FPT.EDU.VN))";

    //Trước
    void first_NH() {
        this.index = 0;
        edit_NH();
    }

    //Về trước
    void prev_NH() {
        if (this.index > 0) {
            this.index--;
            edit_NH();
        } else {
            last_NH();
        }
    }

    //Về sau
    void next_NH() {
        if (this.index < tblQLNguoiHoc.getRowCount() - 1) {
            this.index++;
            edit_NH();
        } else {
            first_NH();
        }
    }

    //Sau
    void last_NH() {
        this.index = tblQLNguoiHoc.getRowCount() - 1;
        edit_NH();
    }

    //vị trí 
    void edit_NH() {
        String ma = (String) tblQLNguoiHoc.getValueAt(index, 0);
        NguoiHoc nh = NguoiHocDAO.selectById(ma);
        this.setForm_NH(nh);
    }

    //hiểm thị người học lên form
    private void setForm_NH(NguoiHoc nh) {
        txt4A_MaNguoiHoc.setText(nh.getMaNguoiHoc());
        txt4A_HoTen.setText(nh.getHoTen());
        cbo4A_GioiTinh.setSelectedItem(nh.getGioiTinh());
        txt4A_NgaySinh.setText(nh.getNgaySinh());
        txt4A_SoDienThoai.setText(nh.getSdt());
        txt4A_Email.setText(nh.getEmail());
        txt4A_DiaChi.setText(nh.getDiaChi());
        lbl4A_Anh.setText("");
        lbl4A_Anh.setIcon(new ImageIcon(ImageHelper.anhChuan(listNguoiHoc.get(index).getAnh(), lbl4A_Anh.getWidth(), lbl4A_Anh.getHeight())));
        txt4A_GhiChu.setText(nh.getGhiChu());
        txt4A_NgayDangKy.setText(nh.getNgayDangKy());
        tblQLNguoiHoc.setRowSelectionInterval(index, index);
    }

    //tạo người học từ form 
    NguoiHoc getForm_NH() {
        NguoiHoc nh = new NguoiHoc();
        nh.setMaNguoiHoc(txt4A_MaNguoiHoc.getText());
        nh.setHoTen(txt4A_HoTen.getText());
        nh.setGioiTinh(cbo4A_GioiTinh.getSelectedItem().toString());
        nh.setNgaySinh(txt4A_NgaySinh.getText());
        nh.setSdt(txt4A_SoDienThoai.getText());
        nh.setEmail(txt4A_Email.getText());
        nh.setDiaChi(txt4A_DiaChi.getText());
        nh.setGhiChu(txt4A_GhiChu.getText());
        nh.setNguoiTao(lblMaNhanVien.getText());
        nh.setNgayDangKy(txt4A_NgayDangKy.getText());
        return nh;
    }

    // Làm mới text
    private void Moi_NH() {
        txt4A_MaNguoiHoc.setText("");
        txt4A_HoTen.setText("");
        cbo4A_GioiTinh.setVisible(true);
        txt4A_NgaySinh.setText("");
        txt4A_SoDienThoai.setText("");
        txt4A_Email.setText("");
        txt4A_DiaChi.setText("");
        lbl4A_Anh.setText("Ảnh");
        lbl4A_Anh.setIcon(null);
        txt4A_GhiChu.setText("");

//        btn4A_Them.setEnabled(true);
        tblQLNguoiHoc.clearSelection();

        txt4A_MaNguoiHoc.setEnabled(true);
        txt4A_NgayDangKy.setText(String.valueOf(localDate.getDayOfMonth()) + "-" + String.valueOf(localDate.getMonth().getValue()) + "-" + String.valueOf(localDate.getYear()));
    }

    //Xóa dữ liệu trên bạn
    private void Xoa_NH() {
        boolean confirm = MessageHelper.confirm(this, "Xóa", "Bạn có thực sự muốn xóa người học này ?", linkAnhHoi);
        if (confirm == true) {
            if (tblQLNguoiHoc.getSelectedRow() >= 0) {
                String MaNH = (String) tblQLNguoiHoc.getValueAt(index, 0);
                NguoiHocDAO.delete(MaNH);
                taiListNguoiHoc();
                Moi_NH();
                taiListLai();
                taiCBO();
                MessageHelper.alert(this, "Xóa thành công", "Thông báo", linkAnhDung);
            } else {
                MessageHelper.error(this, "Bạn chưa chọn người học muốn xóa", linkAnhSai);
            }
        } else {
//            MessageHelper.error(this, "Xóa không thành công", linkAnhSai);
        }
    }

    //Chọn file hình ảnh 
    private void chonHinh_NH() {
        JFileChooser chooser = new JFileChooser("image/nguoihoc/");
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        if (f != null) {
            linkAnhNguoiHoc = f.getPath();
            ImageIcon hinhanh = new ImageIcon(new ImageIcon(linkAnhNguoiHoc).getImage().getScaledInstance(lbl4A_Anh.getWidth(), lbl4A_Anh.getHeight(), Image.SCALE_SMOOTH));

            lbl4A_Anh.setText("");
            lbl4A_Anh.setIcon(hinhanh);
        } else {
            linkAnhNguoiHoc = "";
        }
    }

    //người học mới vào bạn
//người học mới vào bạn
    private void Them_NH() {
        if (tblQLNguoiHoc.getSelectedRow() >= 0) {
            MessageHelper.alert(this, "KHÔNG THỂ THÊM NGƯỜI HỌC TRONG LÚC ĐANG CHỌN NGƯỜI HỌC KHÁC!", "THÔNG BÁO", linkAnhSai);
        } else {
            try {
                //bắt lỗi trùng mã
                if (NguoiHocDAO.checkEqualsMa_NH(txt4A_MaNguoiHoc.getText().trim()) == true) {
                    MessageHelper.error(this, "Mã người học không được trùng", linkAnhSai);
                    txt4A_MaNguoiHoc.requestFocus();
                    return;
                }
                // Bắt lỗi tên
                if (!kiemTraTen(txt4A_HoTen.getText())) {
                    MessageHelper.error(this, "Tên người học không được chứa số hoặc ký tự", linkAnhSai);
                    txt4A_HoTen.requestFocus();
                    return;
                }
                // Bắt lỗi ngày sinh
                if (!kiemTraNgay(txt4A_NgaySinh.getText())) {
                    MessageHelper.error(this, "Ngày sinh không đúng định dạng (01-01-2021)", linkAnhSai);
                    txt4A_NgaySinh.requestFocus();
                    return;
                }
                // Bắt lỗi ngày đăng ký
                if (!kiemTraNgay(txt4A_NgayDangKy.getText())) {
                    MessageHelper.error(this, "Ngày đăng ký không đúng định dạng (01-01-2021)", linkAnhSai);
                    txt4A_NgayDangKy.requestFocus();
                    return;
                }
                //bắt lỗi trùng sdt
                if (NguoiHocDAO.checkEqualsSDT_NH(txt4A_SoDienThoai.getText().trim()) == true) {
                    MessageHelper.error(this, "SDT người học không được trùng", linkAnhSai);
                    txt4A_SoDienThoai.requestFocus();
                    return;
                }
                //bắt lỗi trùng email
                if (NguoiHocDAO.checkEqualsEmail_NH(txt4A_Email.getText().trim()) == true) {
                    MessageHelper.error(this, "Email người học không được trùng", linkAnhSai);
                    txt4A_Email.requestFocus();
                    return;
                }

                //lớn hơn 16 tuổi
                if (!xetNgay(txt4A_NgaySinh.getText(), "nho", "now16")) {
                    MessageHelper.error(this, "Người học ít nhất 16 tuổi", linkAnhSai);
                    txt4A_NgaySinh.requestFocus();
                    return;
                }

//            // Ngày đăng ký
//            if (!xetNgay(txt4A_NgayDangKy.getText(), "lon", "now")) {
//                MessageHelper.error(this, "Ngày đăng ký không được trước ngày hôm nay", linkAnhSai);
//                txt4A_NgayDangKy.requestFocus();
//                return;
//            }
            } catch (Exception ex) {
                System.out.println("loi them nh: " + ex);
            }//bắt lỗi các text

            if (ReadForm_NH() == true) {
                try {
                    NguoiHoc nh = getForm_NH();
                    if (linkAnhNguoiHoc.equals("")) {
                        NguoiHocDAO.insert(nh, "image/nguoihoc/nh00.png");
                    } else {
                        NguoiHocDAO.insert(nh, linkAnhNguoiHoc);
                    }
                    Moi_NH();
                    taiListLai();
                    MessageHelper.alert(this, "Thêm thành công", "Thông báo", linkAnhDung);
                    taiListNguoiHoc();
                } catch (Exception e) {
//                MessageHelper.error(this, "Thêm thất bại", linkAnhSai);
                }
            }
        }
    }
    
    //Cập nhật người học trong bảng
    private void Sua_NH() {
        //bắt lõi không chọn dòng
        if (tblQLNguoiHoc.getSelectedRow() >= 0) {
            try {
                try {
                    // Bắt lỗi tên
                    if (!kiemTraTen(txt4A_HoTen.getText())) {
                        MessageHelper.error(this, "Tên người học không được chứa số hoặc ký tự", linkAnhSai);
                        txt4A_HoTen.requestFocus();
                        return;
                    }

                    // Bắt lỗi ngày sinh
                    if (!kiemTraNgay(txt4A_NgaySinh.getText())) {
                        MessageHelper.error(this, "Ngày sinh không đúng định dạng (01-01-2021)", linkAnhSai);
                        txt4A_NgaySinh.requestFocus();
                        return;
                    }

                    // Bắt lỗi ngày đăng ký
                    if (!kiemTraNgay(txt4A_NgayDangKy.getText())) {
                        MessageHelper.error(this, "Ngày đăng ký không đúng định dạng (01-01-2021)", linkAnhSai);
                        txt4A_NgayDangKy.requestFocus();
                        return;
                    }

                    //Bắt lỗi trùng sdt
                    if (NguoiHocDAO.checkEqualsPhoneUpdate_NH(txt4A_MaNguoiHoc.getText().trim(), txt4A_SoDienThoai.getText().trim()) == true) {
                        MessageHelper.error(this, "SDT người học không được trùng", linkAnhSai);
                        txt4A_SoDienThoai.requestFocus();
                        return;
                    }

                    //Bắt lỗi trùng email
                    if (NguoiHocDAO.checkEqualsEmailUpdate_NH(txt4A_MaNguoiHoc.getText().trim(), txt4A_Email.getText().trim()) == true) {
                        MessageHelper.error(this, "Email người học không được trùng", linkAnhSai);
                        txt4A_Email.requestFocus();
                        return;
                    }

                    //lớn hơn 16 tuổi
                    if (!xetNgay(txt4A_NgaySinh.getText(), "nho", "now16")) {
                        MessageHelper.error(this, "Người học ít nhất 16 tuổi", linkAnhSai);
                        txt4A_NgaySinh.requestFocus();
                        return;
                    }

                    // Ngày đăng ký
                    if (!xetNgay(txt4A_NgayDangKy.getText(), "lon", "now")) {
                        MessageHelper.error(this, "Ngày đăng ký từ hôm nay", linkAnhSai);
                        txt4A_NgayDangKy.requestFocus();
                        return;
                    }

                } catch (Exception e) {

                }
            } catch (Exception ex) {
                System.out.println("loi cap nhat nh: " + ex);
            }
            //bắt lỗi trên các text
            if (ReadForm_NH() == true) {
                NguoiHoc nh = getForm_NH();
                try {
                    if (linkAnhNguoiHoc.equals("")) {
                        NguoiHocDAO.update(nh, nh.getMaNguoiHoc());
                    } else {
                        NguoiHocDAO.update(nh, nh.getMaNguoiHoc(), linkAnhNguoiHoc);
                    }
                    taiListNguoiHoc();
                    taiListLai();
                    MessageHelper.alert(this, "Cập nhật thành công", "Thông báo", linkAnhDung);
                    Moi_NH();
//                    btn4A_Them.setEnabled(true);
                } catch (Exception e) {
                    MessageHelper.error(this, "Cập nhật thất bại", linkAnhSai);
                }
            }
        } else {
            MessageHelper.error(this, "Bạn chưa chọn người học để cập nhật", linkAnhSai);
        }
    }
    //tìm kiếm dữ liệu trong bảng

    private void Tim_NH() {
        DefaultTableModel model = (DefaultTableModel) tblQLNguoiHoc.getModel();
        model.setRowCount(0);
        try {
            String tim = txt4B_TimKiem.getText().trim();
            listNguoiHoc = NguoiHocDAO.selectByTimKiem(tim);
            for (NguoiHoc nh : listNguoiHoc) {
                model.addRow(new Object[]{nh.getMaNguoiHoc(), nh.getHoTen(), nh.getGioiTinh(), nh.getNgaySinh(), nh.getSdt(), nh.getEmail(), nh.getDiaChi(), "Chưa có", nh.getGhiChu(), nh.getNguoiTao(), nh.getNgayDangKy()});
            }
        } catch (Exception e) {
            MessageHelper.error(this, "lỗi truy vấn" + e, linkAnhDung);
        }
    }

    //Bắt lỗi các text
    boolean ReadForm_NH() {
        String ma_nh = txt4A_MaNguoiHoc.getText().trim().toUpperCase();
        String hoten = txt4A_HoTen.getText().trim();
        String ngaysinh = txt4A_NgaySinh.getText().trim();
        String sdt = txt4A_SoDienThoai.getText().trim();
        String email = txt4A_Email.getText().trim();
        String diachi = txt4A_DiaChi.getText().trim();
        String ghichu = txt4A_GhiChu.getText().trim();
        String ngaydangky = txt4A_NgayDangKy.getText().trim();
        boolean dung = true;
        //Bắt lỗi bỏ trống
        if (ma_nh.isEmpty() || hoten.isEmpty() || ngaysinh.isEmpty() || sdt.isEmpty() || email.isEmpty() || diachi.isEmpty() || ghichu.isEmpty() || ngaydangky.isEmpty()) {
            if (ma_nh.isEmpty()) {
                txt4A_MaNguoiHoc.requestFocus();
                dung = false;
                MessageHelper.error(this, "Bạn không được bỏ trống mã", linkAnhSai);
                return dung;
            }
            if (hoten.isEmpty()) {
                txt4A_HoTen.requestFocus();
                dung = false;
                MessageHelper.error(this, "Bạn không được bỏ trống họ tên", linkAnhSai);
                return dung;
            }
            if (ngaysinh.isEmpty()) {
                txt4A_NgaySinh.requestFocus();
                dung = false;
                MessageHelper.error(this, "Bạn không được bỏ trống ngày sinh", linkAnhSai);
                return dung;
            }
            if (sdt.isEmpty()) {
                txt4A_SoDienThoai.requestFocus();
                dung = false;
                MessageHelper.error(this, "Bạn không được bỏ trống sdt", linkAnhSai);
                return dung;
            }
            if (email.isEmpty()) {
                txt4A_Email.requestFocus();
                dung = false;
                MessageHelper.error(this, "Bạn không được bỏ trống email", linkAnhSai);
                return dung;
            }
            if (diachi.isEmpty()) {
                txt4A_DiaChi.requestFocus();
                dung = false;
                MessageHelper.error(this, "Bạn không được bỏ trống dịa chỉ", linkAnhSai);
                return dung;
            }
            if (ghichu.isEmpty()) {
                txt4A_GhiChu.requestFocus();
                dung = false;
                MessageHelper.error(this, "Bạn không được bỏ trống ghi chú", linkAnhSai);
                return dung;
            }
            if (ngaydangky.isEmpty()) {
                txt4A_NgayDangKy.requestFocus();
                dung = false;
                MessageHelper.error(this, "Bạn không được bỏ trống ngày đang ký", linkAnhSai);
                return dung;
            }
            MessageHelper.error(this, "Bạn không được bỏ trống thông tin", linkAnhSai);
        } else {

            //bắt lỗi họ tên
            if (hoten.matches(check1)) {
                MessageHelper.error(this, "Họ tên không được có số", linkAnhSai);
                dung = false;
            }
            //bắt lỗi số điện thoại
            if (!sdt.matches(check2)) {
                MessageHelper.error(this, "Sai định dạng số điện thoại", linkAnhSai);
                dung = false;
            }
            //bắt lỗi email
            if (!email.matches(check3) && !email.matches(check4)) {
                MessageHelper.error(this, "Sai định dạng email", linkAnhSai);
                dung = false;
            }
        }
        return dung;
    }

//______________________________________________________________________________________________________________________________________________________
/*                                                            8 QUẢN LÝ HỌC VIÊN                                                                   */
//______________________________________________________________________________________________________________________________________________________
    // Phương thức xóa học viên khỏi khóa học
    private void xoaHV_HV() {
        int hang = tblQLHocVien_HocVien.getSelectedRow();
        if (hang >= 0) {
            String maKhoaHoc = (String) tblQLHocVien_HocVien.getValueAt(hang, 0);
            String maNguoiHoc = (String) tblQLHocVien_HocVien.getValueAt(hang, 1);
            boolean xoa = MessageHelper.confirm(this, "XÓA HỌC VIÊN NÀY KHỎI KHÓA HỌC", "XÁC NHẬN", linkAnhHoi);
            if (xoa) {
                HocVienDAO.deleteHV_HV(maKhoaHoc, maNguoiHoc);

                taiListLai();
                MessageHelper.alert(this, "XÓA HỌC VIÊN THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
            }
        }
    }

    private void xoaHV_HV2() {
        int hang[] = tblQLHocVien_HocVien.getSelectedRows();
        if (hang.length > 0) {
            String maKhoaHoc = (String) cboQLHocVien_KhoaHoc.getSelectedItem();
            boolean xoa = MessageHelper.confirm(this, "XÓA HỌC VIÊN NÀY KHỎI KHÓA HỌC", "XÁC NHẬN", linkAnhHoi);
            if (xoa) {
                for (int i = 0; i < hang.length; i++) {
                    String maNguoiHoc = (String) tblQLHocVien_HocVien.getValueAt(hang[i], 1);
                    HocVienDAO.deleteHV_HV(maKhoaHoc, maNguoiHoc);
                }
                taiListLai();
                MessageHelper.alert(this, "XÓA HỌC VIÊN THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
            }
        }
    }

    // Phương thức cập nhật điểm tất cả học viên trong khóa học
    private void capNhatDiemHV() {
        try {
            boolean capNhat = MessageHelper.confirm(this, "CẬP NHẬT ĐIỂM HỌC VIÊN?", "XÁC NHẬN", linkAnhHoi);
            if (capNhat) {
                for (int i = 0; i < tblQLHocVien_HocVien.getRowCount(); i++) {
                    try {
                        String maKhoaHoc = String.valueOf(tblQLHocVien_HocVien.getValueAt(i, 0));
                        String maNguoiHoc = String.valueOf(tblQLHocVien_HocVien.getValueAt(i, 1));
                        float diem = Float.valueOf(String.valueOf(tblQLHocVien_HocVien.getValueAt(i, 3)));
                        if (diem < 0 || diem > 10) {
                            MessageHelper.alert(this, "ĐIỂM TỪ 0 ~ 10!", "THÔNG BÁO", linkAnhSai);
                            return;
                        }
                        HocVienDAO.updateHV_HV(diem, maKhoaHoc, maNguoiHoc);
                    } catch (Exception e) {
                        System.out.println("loi diem: " + e);
                        MessageHelper.alert(this, "ĐIỂM KHÔNG ĐƯỢC CHỨA KÝ TỰ!", "THÔNG BÁO", linkAnhSai);
                        return;
                    }
                }
            } else {
                return;
            }
            taiListHocVien_HV((String) cboQLHocVien_KhoaHoc.getSelectedItem());
            taiListHocVien_NH((String) cboQLHocVien_KhoaHoc.getSelectedItem());
            MessageHelper.alert(this, "CẬP NHẬT ĐIỂM THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
        } catch (Exception e) {
            System.out.println("loi diem: " + e);
        }
    }

    // Phương thức tìm kiếm học viên trong khóa học
    private void timKiemHV_HV() {
        String timKiem = txt5A_TimKiem.getText().trim();

        listHocVien_HV = HocVienDAO.selectHV_HV_TimKiem((String) cboQLHocVien_KhoaHoc.getSelectedItem(), timKiem);
        model = (DefaultTableModel) tblQLHocVien_HocVien.getModel();
        model.setRowCount(0);
        for (Object[] hv : listHocVien_HV) {
            model.addRow(new Object[]{hv[0], hv[1], hv[2], hv[3]});
        }
    }

    // Phương thức thêm học viên mới vào khóa học
    private void themHV_NH() {
        int hang = tblQLHocVien_NguoiHoc.getSelectedRow();
        if (hang >= 0) {
            String maKhoaHoc = (String) cboQLHocVien_KhoaHoc.getSelectedItem();
            String maNguoiHoc = (String) tblQLHocVien_NguoiHoc.getValueAt(hang, 0);

            if (maKhoaHoc == null) {
                MessageHelper.alert(this, "CHƯA CÓ KHÓA HỌC!", "THÔNG BÁO", linkAnhSai);
                return;
            }
            HocVienDAO.insert(new HocVien(maNguoiHoc, maKhoaHoc, 0));

            taiListLai();
            MessageHelper.alert(this, "THÊM HỌC VIÊN THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
        }
    }

    // Phương thức thêm học viên mới vào khóa học
    private void themHV_NH2() {
        int hang[] = tblQLHocVien_NguoiHoc.getSelectedRows();
        if (hang.length > 0) {
            String maKhoaHoc = (String) cboQLHocVien_KhoaHoc.getSelectedItem();
            if (maKhoaHoc == null) {
                MessageHelper.alert(this, "CHƯA CÓ KHÓA HỌC!", "THÔNG BÁO", linkAnhSai);
                return;
            }
            for (int i = 0; i < hang.length; i++) {
                String maNguoiHoc = (String) tblQLHocVien_NguoiHoc.getValueAt(hang[i], 0);
                HocVienDAO.insert(new HocVien(maNguoiHoc, maKhoaHoc, 0));
            }
            taiListLai();
            MessageHelper.alert(this, "THÊM HỌC VIÊN THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
        }
    }

    // Phương thức tìm kiếm người học trong khóa học
    private void timKiemHV_NH() {
        String timKiem = txt5B_TimKiem.getText().trim();

        listHocVien_NH = HocVienDAO.selectHV_NH_TimKiem((String) cboQLHocVien_KhoaHoc.getSelectedItem(), timKiem);
        model = (DefaultTableModel) tblQLHocVien_NguoiHoc.getModel();
        model.setRowCount(0);
        for (NguoiHoc nh : listHocVien_NH) {
            model.addRow(new Object[]{nh.getMaNguoiHoc(), nh.getHoTen(), nh.getGioiTinh(), nh.getNgaySinh(), nh.getSdt(), nh.getEmail(), nh.getDiaChi()});
        }
    }

//______________________________________________________________________________________________________________________________________________________
/*                                                           9 QUẢN LÝ NHÂN VIÊN                                                                   */
//______________________________________________________________________________________________________________________________________________________
    byte[] linkAnhMacDinhNam = ImageHelper.imageToByte("image/nhanvien/macDinhNam.jpg");
    byte[] linkAnhMacDinhNu = ImageHelper.imageToByte("image/nhanvien/macDinhNu.jpg");
    int kiemTraCoClickVaoLabelKhong = 0;
    String formGmail = "[a-zA-Z]+(\\d||\\w)*((@gmail.com)|(@GMAIL.COM))";
    String formFpoly = "[a-yA-Y]{2,}((pc)|(PC))\\d{5}((@fpt.edu.vn)|(@FPT.EDU.VN))";
//    String formHoTen = "[a-zA-Z]{1,}[ ][a-zA-Z ]+";
    String formSdtDiDong = "0[35789]{1}\\d{8}";
    String formSdtBan = "02\\d{9}";
    String formMaNhanVien = "((NV)||(nv)||(nV)||(Nv))\\d{1,}";
    String formCCCD = "\\d{12}";

    //Hàm làm mới Form cập nhật thông tin nhân viên
    private void clearNhanVien() {
        txt6A_MaNhanVien.setText("");
        txt6A_HoTen.setText("");
        cbo6A_GioiTinh.setSelectedIndex(0);
        txt6A_NgaySinh.setText("");
        txt6A_SoDienThoai.setText("");
        txt6A_Email.setText("");
        txt6A_DiaChi.setText("");
        lbl6A_Anh.setIcon(null);
        lbl6A_Anh.setText("Ảnh");
        linkAnhNhanVien = "";
        txt6A_CCCD.setText("");
        cbo6A_ChucVu.setSelectedIndex(0);
        pwd6A_MatKhau.setText("");

        //bỏ chọn nhân viên trên bảng
        tblQLNhanVien.clearSelection();
        txt6A_MaNhanVien.setEnabled(true);
    }

    //Hàm làm mới Form cập nhật thông tin nhân viên trong trường hợp đang chọn dòng trên bảng nhân viên
    private void clearNhanVien_DangChonTrenBang() {
        txt6A_HoTen.setText("");
        cbo6A_GioiTinh.setSelectedIndex(0);
        txt6A_NgaySinh.setText("");
        txt6A_SoDienThoai.setText("");
        txt6A_Email.setText("");
        txt6A_DiaChi.setText("");
        lbl6A_Anh.setIcon(null);
        lbl6A_Anh.setText("Ảnh");
        linkAnhNhanVien = "";
        txt6A_CCCD.setText("");
        cbo6A_ChucVu.setSelectedIndex(0);
        pwd6A_MatKhau.setText("");
    }

    //Hàm viết đối tượng nhân viên lên form
    private void writeForm(NhanVien nv) {
        txt6A_MaNhanVien.setText(nv.getMaNhanVien());
        txt6A_HoTen.setText(nv.getHoTen());
        cbo6A_GioiTinh.setSelectedItem(nv.getGioiTinh());
        txt6A_NgaySinh.setText(nv.getNgaySinh());
        txt6A_SoDienThoai.setText(nv.getSdt());
        txt6A_Email.setText(nv.getEmail());
        txt6A_DiaChi.setText(nv.getDiaChi());
        lbl6A_Anh.setIcon(new ImageIcon(ImageHelper.anhChuan(ImageHelper.byteToImage(nv.getAnh()), lbl6A_Anh.getWidth(), lbl6A_Anh.getHeight())));
        lbl6A_Anh.setText("");
        txt6A_CCCD.setText(nv.getCCCD());
        cbo6A_ChucVu.setSelectedItem(nv.getChucVu());
        pwd6A_MatKhau.setText(nv.getMatKhau());

        //Biến kiểm tra đã click vô Label hay chưa để khi update sẽ xét nếu chưa click thì dùng ảnh cũ, click rồi thì lấy link ảnh và thay đổi
        kiemTraCoClickVaoLabelKhong = 0;

    }

    //Hàm kiểm tra dữ liệu nhân viên nhập vào có hợp lệ không
    private boolean checkNhanVien() {
        //Trường hợp một trong các textField bỏ trống
        if (txt6A_MaNhanVien.getText().trim().isEmpty() || txt6A_HoTen.getText().trim().isEmpty() || txt6A_NgaySinh.getText().trim().isEmpty() || txt6A_SoDienThoai.getText().trim().isEmpty() || txt6A_Email.getText().trim().isEmpty() || txt6A_DiaChi.getText().trim().isEmpty() || txt6A_CCCD.getText().trim().isEmpty() || pwd6A_MatKhau.getText().trim().isEmpty()) {
            //Trường hợp mã nhân viên bỏ trống
            if (txt6A_MaNhanVien.getText().trim().isEmpty()) {
                MessageHelper.alert(this, "KHÔNG ĐƯỢC BỎ TRỐNG MÃ NHÂN VIÊN!", "THÔNG BÁO", linkAnhSai);
                txt6A_MaNhanVien.requestFocus();
                return false;
            }

            //Trường hợp họ tên viên bỏ trống
            if (txt6A_HoTen.getText().trim().isEmpty()) {
                MessageHelper.alert(this, "KHÔNG ĐƯỢC BỎ TRỐNG HỌ TÊN!", "THÔNG BÁO", linkAnhSai);
                txt6A_HoTen.requestFocus();
                return false;
            }

            //Trường hợp ngày sinh bỏ trống
            if (txt6A_NgaySinh.getText().trim().isEmpty()) {
                MessageHelper.alert(this, "KHÔNG ĐƯỢC BỎ TRỐNG NGÀY SINH!", "THÔNG BÁO", linkAnhSai);
                txt6A_NgaySinh.requestFocus();
                return false;
            }

            //Trường hợp số điện thoại bỏ trống
            if (txt6A_SoDienThoai.getText().trim().isEmpty()) {
                MessageHelper.alert(this, "KHÔNG ĐƯỢC BỎ TRỐNG SỐ ĐIỆN THOẠI!", "THÔNG BÁO", linkAnhSai);
                txt6A_SoDienThoai.requestFocus();
                return false;
            }

            //Trường hợp email bỏ trống
            if (txt6A_Email.getText().trim().isEmpty()) {
                MessageHelper.alert(this, "KHÔNG ĐƯỢC BỎ TRỐNG EMAIL!", "THÔNG BÁO", linkAnhSai);
                txt6A_Email.requestFocus();
                return false;
            }

            //Trường hợp địa chỉ bỏ trống
            if (txt6A_DiaChi.getText().trim().isEmpty()) {
                MessageHelper.alert(this, "KHÔNG ĐƯỢC BỎ TRỐNG ĐỊA CHỈ!", "THÔNG BÁO", linkAnhSai);
                txt6A_DiaChi.requestFocus();
                return false;
            }

            //Trường hợp cccd bỏ trống
            if (txt6A_CCCD.getText().trim().isEmpty()) {
                MessageHelper.alert(this, "KHÔNG ĐƯỢC BỎ TRỐNG CCCD!", "THÔNG BÁO", linkAnhSai);
                txt6A_CCCD.requestFocus();
                return false;
            }

            //Trường hợp mật khẩu bỏ trống
            if (pwd6A_MatKhau.getText().trim().isEmpty()) {
                MessageHelper.alert(this, "KHÔNG ĐƯỢC BỎ TRỐNG MẬT KHẨU!", "THÔNG BÁO", linkAnhSai);
                pwd6A_MatKhau.requestFocus();
                return false;
            }

        } //Trường hợp đã nhập đầy đủ các trường dữ liệu
        else {
            //Xét điều kiện Mã Nhân Viên phải theo dạng (NV)|(nv)...
            if (!txt6A_MaNhanVien.getText().trim().matches(formMaNhanVien)) {
                MessageHelper.alert(this, "MÃ NHÂN VIÊN KHÔNG HỢP LỆ!", "THÔNG BÁO", linkAnhSai);
                txt6A_MaNhanVien.requestFocus();
                return false;
            }

            // Xét họ tên
            if (!kiemTraTen(txt6A_HoTen.getText())) {
                MessageHelper.alert(this, "TÊN KHÔNG ĐƯỢC CHỨA SỐ HOẶC KÝ TỰ!", "THÔNG BÁO", linkAnhSai);
                txt6A_HoTen.requestFocus();
                return false;
            }
            //Xét điều kiện ngày sinh phải thuộc dạng (dd-MM-yyyy)
            if (!kiemTraNgay(txt6A_NgaySinh.getText())) {
                MessageHelper.alert(this, "NGÀY SINH KHÔNG ĐÚNG ĐỊNH DẠNG (01-01-2021)!", "THÔNG BÁO", linkAnhSai);
                txt6A_NgaySinh.requestFocus();
                return false;
            }

            // Nhân viên ít nhất 18 tuổi
            if (!xetNgay(txt6A_NgaySinh.getText(), "nho", "now18")) {
                MessageHelper.alert(this, "NHÂN VIÊN ÍT NHẤT 18 TUỔI!", "THÔNG BÁO", linkAnhSai);
                txt6A_NgaySinh.requestFocus();
                return false;
            }

            //Xét điều kiện số điện thoại phải chuẩn sđt việt nam
            if (!txt6A_SoDienThoai.getText().trim().matches(formSdtBan) && !txt6A_SoDienThoai.getText().trim().matches(formSdtDiDong)) {
                MessageHelper.alert(this, "SỐ ĐIỆN THOẠI KHÔNG HỢP LỆ!", "THÔNG BÁO", linkAnhSai);
                txt6A_SoDienThoai.requestFocus();
                return false;
            }

            //Xét điều kiện email phải chuẩn @gmail.com hoặc @fpt.edu.vn
            if (!txt6A_Email.getText().trim().matches(formGmail) && !txt6A_Email.getText().trim().matches(formFpoly)) {
                MessageHelper.alert(this, "EMAIL KHÔNG HỢP LỆ!", "THÔNG BÁO", linkAnhSai);
                txt6A_Email.requestFocus();
                return false;
            }

            //Xét điều kiện CCCD phải 12 số chuẩn CCCD VN hiện tại
            if (!txt6A_CCCD.getText().trim().matches(formCCCD)) {
                MessageHelper.alert(this, "CCCD KHÔNG HỢP LỆ!", "THÔNG BÁO", linkAnhSai);
                txt6A_CCCD.requestFocus();
                return false;
            }

            //Xét mật khẩu phải từ 6-16 kí tự
            if (pwd6A_MatKhau.getText().trim().length() < 6 || pwd6A_MatKhau.getText().trim().length() > 16) {
                MessageHelper.alert(this, "MẬT KHẨU KHÔNG HỢP LỆ!", "THÔNG BÁO", linkAnhSai);
                pwd6A_MatKhau.requestFocus();
                return false;
            }
        }
        return true;
    }

    //Kiểm tra có tồn tại ảnh hay chưa
    private String kiemTraAnh(byte[] anh) {
        String ketQua = "";
        if (Arrays.equals(linkAnhMacDinhNam, anh) || Arrays.equals(linkAnhMacDinhNu, anh)) {
            ketQua = "Chưa có ảnh";
        } else {
            ketQua = "Đã có ảnh";
        }
        return ketQua;
    }

    //Hàm thêm dữ liệu nhân viên vào bảng
    private void themNhanVien() {
        int kiemTra = 0;
        NhanVien nv = new NhanVien();
        if (tblQLNhanVien.getSelectedRow() >= 0) {
            MessageHelper.alert(this, "KHÔNG THỂ THÊM NHÂN VIÊN TRONG LÚC ĐANG CHỌN NHÂN VIÊN KHÁC!", "THÔNG BÁO", linkAnhSai);
        } else {
            if (!checkNhanVien()) {
            } else {
                for (int i = 0; i < listNhanVien.size(); i++) {
                    if (listNhanVien.get(i).getMaNhanVien().equalsIgnoreCase(txt6A_MaNhanVien.getText().trim())) {
                        MessageHelper.alert(this, "KHÔNG ĐƯỢC NHẬP TRÙNG MÃ NHÂN VIÊN!", "THÔNG BÁO", linkAnhSai);
                        kiemTra = 1;
                        break;
                    }

                }
                if (kiemTra == 1) {
                } else {
                    nv.setMaNhanVien(txt6A_MaNhanVien.getText().trim());
                    nv.setHoTen(txt6A_HoTen.getText().trim());
                    nv.setGioiTinh((String) cbo6A_GioiTinh.getSelectedItem());
                    nv.setNgaySinh(txt6A_NgaySinh.getText().trim());
                    nv.setSdt(txt6A_SoDienThoai.getText().trim());
                    nv.setEmail(txt6A_Email.getText().trim());
                    nv.setDiaChi(txt6A_DiaChi.getText().trim());
                    nv.setCCCD(txt6A_CCCD.getText().trim());
                    nv.setChucVu((String) cbo6A_ChucVu.getSelectedItem());
                    nv.setMatKhau(pwd6A_MatKhau.getText().trim());

                    if (linkAnhNhanVien.isEmpty()) {
                        if (cbo6A_GioiTinh.getSelectedItem().equals("Nam")) {
                            NhanVienDAO.insertNoAvatarNam(nv);
                            taiListNhanVien();
                            MessageHelper.alert(this, "THÊM NHÂN VIÊN THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                            clearNhanVien();
                        } else {
                            NhanVienDAO.insertNoAvatarNu(nv);
                            taiListNhanVien();
                            MessageHelper.alert(this, "THÊM NHÂN VIÊN THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                            clearNhanVien();
                        }

                    } else {
                        NhanVienDAO.insert(nv, linkAnhNhanVien);
                        taiListNhanVien();
                        MessageHelper.alert(this, "THÊM NHÂN VIÊN THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                        clearNhanVien();
                    }
                }
            }
        }
    }

    //Hàm update Nhân Viên
    private void suaNhanVien() {
        NhanVien nv = new NhanVien();
        if (tblQLNhanVien.getSelectedRow() >= 0) {
            if (!checkNhanVien()) {
            } else {
                nv.setMaNhanVien(txt6A_MaNhanVien.getText().trim());
                nv.setHoTen(txt6A_HoTen.getText().trim());
                nv.setGioiTinh((String) cbo6A_GioiTinh.getSelectedItem());
                nv.setNgaySinh(txt6A_NgaySinh.getText().trim());
                nv.setSdt(txt6A_SoDienThoai.getText().trim());
                nv.setEmail(txt6A_Email.getText().trim());
                nv.setDiaChi(txt6A_DiaChi.getText().trim());
                nv.setCCCD(txt6A_CCCD.getText().trim());
                nv.setChucVu((String) cbo6A_ChucVu.getSelectedItem());
                nv.setMatKhau(pwd6A_MatKhau.getText().trim());

                if (kiemTraCoClickVaoLabelKhong == 1) {
                    if (linkAnhNhanVien.isEmpty()) {
                        if (cbo6A_GioiTinh.getSelectedItem().equals("Nam")) {
                            NhanVienDAO.updateNoAvatarNam(nv, txt6A_MaNhanVien.getText());
                            taiListNhanVien();
                            MessageHelper.alert(this, "CẬP NHẬT NHÂN VIÊN THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                            clearNhanVien();
                        } else {
                            NhanVienDAO.updateNoAvatarNu(nv, txt6A_MaNhanVien.getText());
                            taiListNhanVien();
                            MessageHelper.alert(this, "CẬP NHẬT NHÂN VIÊN THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                            clearNhanVien();
                        }
                    } else {
                        NhanVienDAO.update(nv, txt6A_MaNhanVien.getText(), linkAnhNhanVien);
                        taiListNhanVien();
                        MessageHelper.alert(this, "CẬP NHẬT NHÂN VIÊN THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                        clearNhanVien();
                    }
                } else {
                    NhanVienDAO.updateGiuNguyenAvatar(nv, txt6A_MaNhanVien.getText());
                    taiListNhanVien();
                    MessageHelper.alert(this, "CẬP NHẬT NHÂN VIÊN THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                    clearNhanVien();
                }
            }
        } else {
            MessageHelper.alert(this, "CHƯA CHỌN NHÂN VIÊN ĐỂ CẬP NHẬT!", "THÔNG BÁO", linkAnhSai);
        }
    }

    //Hàm xóa nhân viên
    private void xoaNhanVien() {
        if (tblQLNhanVien.getSelectedRow() >= 0) {
            if (txtB1_TaiKhoan.getText().trim().equalsIgnoreCase(txt6A_MaNhanVien.getText().trim())) {
                MessageHelper.alert(this, "KHÔNG THỂ XÓA TÀI KHOẢN ĐANG SỬ DỤNG!", "THÔNG BÁO", linkAnhSai);
            } else {
                boolean xoa = MessageHelper.confirm(this, "XÓA NHÂN VIÊN?", "XÁC NHẬN", linkAnhHoi);
                if (xoa) {
                    NhanVienDAO.delete(txt6A_MaNhanVien.getText());
                    taiListNhanVien();
                    taiListKhoaHoc();
                    taiListNguoiHoc();
                    taiListLai();
                    MessageHelper.alert(this, "XÓA NHÂN VIÊN THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                    clearNhanVien();
                }
            }
        } else {
            MessageHelper.alert(this, "CHƯA CHỌN NHÂN VIÊN ĐỂ XÓA!", "THÔNG BÁO", linkAnhSai);
        }
    }

    //Hàm tìm kiếm nhân viên
    private void timKiemNhanVien() {
        String tuKhoa = txt6B_TimKiem.getText().trim();
        //truy vấn từ khóa
        listNhanVien = NhanVienDAO.selectAll(tuKhoa);
        //fill lên bảng
        model = (DefaultTableModel) tblQLNhanVien.getModel();
        model.setRowCount(0);
        for (NhanVien nv : listNhanVien) {
            model.addRow(new Object[]{nv.getMaNhanVien(), nv.getHoTen(), nv.getGioiTinh(), nv.getNgaySinh(), nv.getSdt(), nv.getEmail(), nv.getDiaChi(), kiemTraAnh(nv.getAnh()), nv.getCCCD(), nv.getChucVu(), "*****"});
        }
    }

    //Hàm chọn lbl Ảnh sẽ hiển thị lên
    private void hienThiAnh() {
        try {
            kiemTraCoClickVaoLabelKhong = 1;
            JFileChooser jfc = new JFileChooser("image/nhanvien");
            jfc.showOpenDialog(null);
            File file = jfc.getSelectedFile();
            Image img = ImageIO.read(file);
            lbl6A_Anh.setText("");
            linkAnhNhanVien = file.getPath();
            lbl6A_Anh.setIcon(new ImageIcon(img.getScaledInstance(lbl6A_Anh.getWidth(), lbl6A_Anh.getHeight(), 0)));
        } catch (Exception e) {
            kiemTraCoClickVaoLabelKhong = 1;
            lbl6A_Anh.setIcon(null);
            lbl6A_Anh.setText("Ảnh");
            linkAnhNhanVien = "";
        }
    }

    //Nút First
    private void TruocNV() {
        this.row = 0;
        writeForm(listNhanVien.get(row));
        tblQLNhanVien.setRowSelectionInterval(0, 0);
    }

    //Nút Last
    private void SauNV() {
        this.row = tblQLNhanVien.getRowCount() - 1;
        writeForm(listNhanVien.get(row));
        tblQLNhanVien.setRowSelectionInterval(row, row);
    }

    //Nút Next
    private void veSauNV() {
        if (this.row < tblQLNhanVien.getRowCount() - 1) {
            this.row++;
            writeForm(listNhanVien.get(row));
            tblQLNhanVien.setRowSelectionInterval(row, row);
        } else {
            TruocNV();
        }
    }

    //Nút Prev
    private void veTruocNV() {
        if (this.row > 0) {
            this.row--;
            writeForm(listNhanVien.get(row));
            tblQLNhanVien.setRowSelectionInterval(row, row);
        } else {
            SauNV();
        }
    }

//______________________________________________________________________________________________________________________________________________________
/*                                                           10 THỐNG KÊ TỔNG HỢP                                                                    */
//______________________________________________________________________________________________________________________________________________________
    // Xuất dữ liệu thống kê ra file excel
    private void xuatTK1() {
        try {
            // Excel
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("THỐNG KÊ 1");

            // Thêm chủ đề thống kê từ dòng 0
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("THỐNG KÊ SỐ LƯỢNG NGƯỜI HỌC");

            // Thêm tiêu đề cột từ dòng 2
            rowhead = sheet.createRow((short) 2);
            rowhead.createCell((short) 0).setCellValue("THỜI GIAN");
            rowhead.createCell((short) 1).setCellValue("SỐ NGƯỜI HỌC");
            rowhead.createCell((short) 2).setCellValue("NGÀY ĐĂNG KÝ ĐẦU TIÊN");
            rowhead.createCell((short) 3).setCellValue("NGÀY ĐĂNG KÝ CUỐI CÙNG");

            // Thêm dữ liệu từ dòng 3
            for (int i = 0; i < listTK1.size(); i++) {
                HSSFRow row = sheet.createRow((short) i + 3);
                row.createCell((short) 0).setCellValue((String) listTK1.get(i)[0]);
                row.createCell((short) 1).setCellValue((int) listTK1.get(i)[1]);
                row.createCell((short) 2).setCellValue((String) listTK1.get(i)[2]);
                row.createCell((short) 3).setCellValue((String) listTK1.get(i)[3]);
            }

            String duongDan = null;
            JFileChooser luuDanDuong;
            if (duongDan == null) {
                luuDanDuong = new JFileChooser();
                luuDanDuong.setSelectedFile(new File(""));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", ".xls", ".xlsx"); // kiểu file
                luuDanDuong.addChoosableFileFilter(filter);

                int returnValue = luuDanDuong.showSaveDialog(this); // kiểu Open file
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    duongDan = luuDanDuong.getSelectedFile().getPath();
                    if (!duongDan.endsWith(".xls")) {
                        duongDan = duongDan + ".xls";
                    }
                    try {
                        workbook.write(new File(duongDan));
                    } catch (Exception e) {
                        MessageHelper.alert(this, "XUẤT DỮ LIỆU THẤT BẠI!", "THÔNG BÁO", linkAnhSai);
                    }
                } else if (returnValue == JFileChooser.CANCEL_OPTION) {
//                        MessageHelper.alert(this, "HỦY XUẤT DỮ LIỆU", "THÔNG BÁO", linkAnhSai);
                    return;
                }
            } else {
                workbook.write(new File(duongDan));
            }
        } catch (Exception e) {
            System.out.println("loi xuat tk1: " + e);
            return;
        }
        MessageHelper.alert(this, "XUẤT DỮ LIỆU THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
    }

    // 
    private void xuatTK2() {
        try {
            // Excel
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("THỐNG KÊ 2");

            // Thêm chủ đề thống kê từ dòng 0
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("THỐNG KÊ ĐIỂM TỪNG KHÓA HỌC");

            // Thêm tiêu đề cột từ dòng 2
            rowhead = sheet.createRow((short) 2);
            rowhead.createCell((short) 0).setCellValue("MÃ NGƯỜI HỌC");
            rowhead.createCell((short) 1).setCellValue("HỌ TÊN");
            rowhead.createCell((short) 2).setCellValue("ĐIỂM TRUNG BÌNH");
            rowhead.createCell((short) 3).setCellValue("HỌC LỰC");

            // Thêm dữ liệu từ dòng 3
            for (int i = 0; i < listTK2.size(); i++) {
                HSSFRow row = sheet.createRow((short) i + 3);
                row.createCell((short) 0).setCellValue((String) listTK2.get(i)[0]);
                row.createCell((short) 1).setCellValue((String) listTK2.get(i)[1]);
                row.createCell((short) 2).setCellValue((double) listTK2.get(i)[2]);
                row.createCell((short) 3).setCellValue((String) listTK2.get(i)[3]);
            }

            String duongDan = null;
            JFileChooser luuDanDuong;
            if (duongDan == null) {
                luuDanDuong = new JFileChooser();
                luuDanDuong.setSelectedFile(new File(""));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", ".xls", ".xlsx"); // kiểu file
                luuDanDuong.addChoosableFileFilter(filter);

                int returnValue = luuDanDuong.showSaveDialog(this); // kiểu Open file
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    duongDan = luuDanDuong.getSelectedFile().getPath();
                    if (!duongDan.endsWith(".xls")) {
                        duongDan = duongDan + ".xls";
                    }
                    try {
                        workbook.write(new File(duongDan));
                    } catch (Exception e) {
                        MessageHelper.alert(this, "XUẤT DỮ LIỆU THẤT BẠI!", "THÔNG BÁO", linkAnhSai);
                    }
                } else if (returnValue == JFileChooser.CANCEL_OPTION) {
//                        MessageHelper.alert(this, "HỦY XUẤT DỮ LIỆU", "THÔNG BÁO", linkAnhSai);
                    return;
                }
            } else {
                workbook.write(new File(duongDan));
            }
        } catch (Exception e) {
            System.out.println("loi xuat tk2: " + e);
            return;
        }
        MessageHelper.alert(this, "XUẤT DỮ LIỆU THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
    }

    // 
    private void xuatTK3() {
        try {
            // Excel
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("THỐNG KÊ 3");

            // Thêm chủ đề thống kê từ dòng 0
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("THỐNG KÊ ĐIỂM TỪNG CHUYÊN ĐỀ");

            // Thêm tiêu đề cột từ dòng 2
            rowhead = sheet.createRow((short) 2);
            rowhead.createCell((short) 0).setCellValue("TÊN CHUYÊN ĐỀ");
            rowhead.createCell((short) 1).setCellValue("SỐ HỌC VIÊN");
            rowhead.createCell((short) 2).setCellValue("ĐIỂM THẤP NHẤT");
            rowhead.createCell((short) 3).setCellValue("ĐIỂM CAO NHẤT");
            rowhead.createCell((short) 4).setCellValue("ĐIỂM TRUNG BÌNH");

            // Thêm dữ liệu từ dòng 3
            for (int i = 0; i < listTK3.size(); i++) {
                HSSFRow row = sheet.createRow((short) i + 3);
                row.createCell((short) 0).setCellValue((String) listTK3.get(i)[0]);
                row.createCell((short) 1).setCellValue((int) listTK3.get(i)[1]);
                row.createCell((short) 2).setCellValue((double) listTK3.get(i)[2]);
                row.createCell((short) 3).setCellValue((double) listTK3.get(i)[3]);
                row.createCell((short) 4).setCellValue((double) listTK3.get(i)[4]);
            }

            String duongDan = null;
            JFileChooser luuDanDuong;
            if (duongDan == null) {
                luuDanDuong = new JFileChooser();
                luuDanDuong.setSelectedFile(new File(""));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", ".xls", ".xlsx"); // kiểu file
                luuDanDuong.addChoosableFileFilter(filter);

                int returnValue = luuDanDuong.showSaveDialog(this); // kiểu Open file
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    duongDan = luuDanDuong.getSelectedFile().getPath();
                    if (!duongDan.endsWith(".xls")) {
                        duongDan = duongDan + ".xls";
                    }
                    try {
                        workbook.write(new File(duongDan));
                    } catch (Exception e) {
                        MessageHelper.alert(this, "XUẤT DỮ LIỆU THẤT BẠI!", "THÔNG BÁO", linkAnhSai);
                    }
                } else if (returnValue == JFileChooser.CANCEL_OPTION) {
//                        MessageHelper.alert(this, "HỦY XUẤT DỮ LIỆU", "THÔNG BÁO", linkAnhSai);
                    return;
                }
            } else {
                workbook.write(new File(duongDan));
            }
        } catch (Exception e) {
            System.out.println("loi xuat tk3: " + e);
            return;
        }
        MessageHelper.alert(this, "XUẤT DỮ LIỆU THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
    }

    // 
    private void xuatTK4() {
        try {
            // Excel
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("THỐNG KÊ 4");

            // Thêm chủ đề thống kê từ dòng 0
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("THỐNG KÊ DOANH THU TỪNG CHUYÊN ĐỀ");

            // Thêm tiêu đề cột từ dòng 2
            rowhead = sheet.createRow((short) 2);
            rowhead.createCell((short) 0).setCellValue("TÊN CHUYÊN ĐỀ");
            rowhead.createCell((short) 1).setCellValue("SỐ KHÓA HỌC");
            rowhead.createCell((short) 2).setCellValue("TỔNG SỐ HỌC VIÊN");
            rowhead.createCell((short) 3).setCellValue("DOANH THU");
            rowhead.createCell((short) 4).setCellValue("HỌC PHÍ THẤT NHẤT");
            rowhead.createCell((short) 5).setCellValue("HỌC PHÍ CAO NHẤT");
            rowhead.createCell((short) 6).setCellValue("HỌC PHÍ TRUNG BÌNH");

            // Thêm dữ liệu từ dòng 3
            for (int i = 0; i < listTK4.size(); i++) {
                HSSFRow row = sheet.createRow((short) i + 3);
                row.createCell((short) 0).setCellValue((String) listTK4.get(i)[0]);
                row.createCell((short) 1).setCellValue((int) listTK4.get(i)[1]);
                row.createCell((short) 2).setCellValue((int) listTK4.get(i)[2]);
                row.createCell((short) 3).setCellValue((long) listTK4.get(i)[3]);
                row.createCell((short) 4).setCellValue((long) listTK4.get(i)[4]);
                row.createCell((short) 5).setCellValue((long) listTK4.get(i)[5]);
                row.createCell((short) 6).setCellValue((long) listTK4.get(i)[6]);
            }

            String duongDan = null;
            JFileChooser luuDanDuong;
            if (duongDan == null) {
                luuDanDuong = new JFileChooser();
                luuDanDuong.setSelectedFile(new File(""));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", ".xls", ".xlsx"); // kiểu file
                luuDanDuong.addChoosableFileFilter(filter);

                int returnValue = luuDanDuong.showSaveDialog(this); // kiểu Open file
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    duongDan = luuDanDuong.getSelectedFile().getPath();
                    if (!duongDan.endsWith(".xls")) {
                        duongDan = duongDan + ".xls";
                    }
                    try {
                        workbook.write(new File(duongDan));
                    } catch (Exception e) {
                        MessageHelper.alert(this, "XUẤT DỮ LIỆU THẤT BẠI!", "THÔNG BÁO", linkAnhSai);
                    }
                } else if (returnValue == JFileChooser.CANCEL_OPTION) {
//                        MessageHelper.alert(this, "HỦY XUẤT DỮ LIỆU", "THÔNG BÁO", linkAnhSai);
                    return;
                }
            } else {
                workbook.write(new File(duongDan));
            }
        } catch (Exception e) {
            System.out.println("loi xuat tk4: " + e);
            return;
        }
        MessageHelper.alert(this, "XUẤT DỮ LIỆU THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
    }

//______________________________________________________________________________________________________________________________________________________
/*                                                           11 TRỢ GIÚP                                                                    */
//______________________________________________________________________________________________________________________________________________________
    //        10.1 HƯỚNG DẪN
    //        10.2 GIỚI THIỆU
    //        10.3 BÁO CÁO    
//Hàm gửi báo cáo
    private void guiBaoCao() {
        try {

            //Lấy các thông tin người dùng nhập vào trên Form
            String fromEmail = "bonconsoingusi@gmail.com";
            String password = "bonconsoingusi4444";
            String toEmail = "bonconsoingusi@gmail.com";
            String subject = txtG1_TieuDe.getText();
            String body = txtG1_NoiDung.getText();

            //Các thông số kết nối tới mail Sever
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //Tạo đối tượng Session đưa vòa các thông tin xác thực tài khoản email
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            message.setSubject(subject);

            //Phần một gồm đoạn tin nhắn
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(body);

            if (link != null) {
                //Phần 2 chứa file
                MimeBodyPart messageBodyPart2 = new MimeBodyPart();
                //Đường dẫn đến file
                String filePath = link;
                DataSource source1 = new FileDataSource(filePath);
                messageBodyPart2.setDataHandler(new DataHandler(source1));
                messageBodyPart2.setFileName(filePath);

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart1);
                multipart.addBodyPart(messageBodyPart2);
                message.setContent(multipart);
                Transport.send(message);
                MessageHelper.alert(this, "GỬI BÁO CÁO THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
                JFrame_BaoCao.setVisible(false);
            } else {
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart1);
                message.setContent(multipart);
                Transport.send(message);
                MessageHelper.alert(this, "GỬI BÁO CÁO THÀNH CÔNG!", "THÔNG BÁO", linkAnhDung);
            }
        } catch (Exception e) {
        }

    }

    String link = null;

    //Hàm chọn file để gửi báo cáo
    private void chonFileBaoCao() {
        JFileChooser chonFile = new JFileChooser("src");
        int ketQua = chonFile.showOpenDialog(null);
        if (ketQua == JFileChooser.APPROVE_OPTION) {
            link = chonFile.getSelectedFile().getPath();
            btnG1_ChonAnh.setText(chonFile.getSelectedFile().getName());
        } else {
            link = null;
            btnG1_ChonAnh.setText("Chọn ảnh");
        }
    }

    // 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JDialog_ChaoMung = new javax.swing.JDialog();
        pnlA1 = new javax.swing.JPanel();
        prgA1 = new javax.swing.JProgressBar();
        lblA1 = new javax.swing.JLabel();
        JFrame_DangNhap = new javax.swing.JFrame();
        pnlB1 = new javax.swing.JPanel();
        pwdB1_MatKhau = new javax.swing.JPasswordField();
        txtB1_TaiKhoan = new javax.swing.JTextField();
        lblB1_HienMK = new javax.swing.JLabel();
        chkB1 = new javax.swing.JCheckBox();
        lblB1_QuenMatKhau = new javax.swing.JLabel();
        btnB1 = new javax.swing.JButton();
        lblB1 = new javax.swing.JLabel();
        JFrame_QuenMatKhau = new javax.swing.JFrame();
        pnlC1 = new javax.swing.JPanel();
        txtC1_TaiKhoan = new javax.swing.JTextField();
        txtC1_Email = new javax.swing.JTextField();
        txtC1_MaXacNhan = new javax.swing.JTextField();
        pwdC1_MatKhauMoi = new javax.swing.JPasswordField();
        pwdC1_XacNhan = new javax.swing.JPasswordField();
        btnC1_GuiMaXacNhan = new javax.swing.JButton();
        lblC1_HienMKM = new javax.swing.JLabel();
        lblC1_HienXN = new javax.swing.JLabel();
        btnC1 = new javax.swing.JButton();
        lblC1_ClearQMK = new javax.swing.JLabel();
        lblC1_QuayLai = new javax.swing.JLabel();
        lblC1_DangGui = new javax.swing.JLabel();
        lblC1 = new javax.swing.JLabel();
        JFrame_DoiMatKhau = new javax.swing.JFrame();
        pnlD1 = new javax.swing.JPanel();
        lblD1_HienMKHT = new javax.swing.JLabel();
        pwdD1_MatKhauHienTai = new javax.swing.JPasswordField();
        lblD1_HienMKM = new javax.swing.JLabel();
        pwdD1_MatKhauMoi = new javax.swing.JPasswordField();
        lblD1_HienXN = new javax.swing.JLabel();
        pwdD1_XacNhan = new javax.swing.JPasswordField();
        btnD1 = new javax.swing.JButton();
        lblD1 = new javax.swing.JLabel();
        JFrame_HuongDan = new javax.swing.JFrame();
        pnlE0 = new javax.swing.JPanel();
        lblE1 = new javax.swing.JLabel();
        pnlE1 = new javax.swing.JPanel();
        btnE1_DangNhap = new javax.swing.JButton();
        btnE1_QuenMatKhau = new javax.swing.JButton();
        btnE1_DoiMatKhau = new javax.swing.JButton();
        btnE1_ChuyenDe = new javax.swing.JButton();
        btnE1_KhoaHoc = new javax.swing.JButton();
        btnE1_NguoiHoc = new javax.swing.JButton();
        btnE1_HocVien = new javax.swing.JButton();
        btnE1_NhanVien = new javax.swing.JButton();
        btnE1_ThongKe = new javax.swing.JButton();
        pnlE1_video = new javax.swing.JPanel();
        JDialog_GioiThieu = new javax.swing.JDialog();
        pnlF1 = new javax.swing.JPanel();
        lblF1_p1 = new javax.swing.JLabel();
        lblF1_p2 = new javax.swing.JLabel();
        lblF1_DiaChi = new javax.swing.JLabel();
        lblF1_LienHe = new javax.swing.JLabel();
        lblF1 = new javax.swing.JLabel();
        JFrame_BaoCao = new javax.swing.JFrame();
        pnlA2 = new javax.swing.JPanel();
        lblG1_BaoCao = new javax.swing.JLabel();
        lblG1_NoiDung = new javax.swing.JLabel();
        srcG1 = new javax.swing.JScrollPane();
        txtG1_NoiDung = new javax.swing.JTextArea();
        lblG1_TieuDe = new javax.swing.JLabel();
        txtG1_TieuDe = new javax.swing.JTextField();
        btnG1_ChonAnh = new javax.swing.JButton();
        btnG1_Gui = new javax.swing.JButton();
        pnlDieuHuong = new javax.swing.JPanel();
        pnlAnhDaiDien = new javax.swing.JPanel();
        lblMaNhanVien = new javax.swing.JLabel();
        lblAnhDaiDien = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        lblTrangChu = new javax.swing.JLabel();
        lblQLChuyenDe = new javax.swing.JLabel();
        lblQLKhoaHoc = new javax.swing.JLabel();
        lblQLNguoiHoc = new javax.swing.JLabel();
        lblQLHocVien = new javax.swing.JLabel();
        lblQLNhanVien = new javax.swing.JLabel();
        lblTKTongHop = new javax.swing.JLabel();
        lblBaoCao = new javax.swing.JLabel();
        lblDangXuat = new javax.swing.JLabel();
        pnlNoiDung = new javax.swing.JPanel();
        pnlTrangThai = new javax.swing.JPanel();
        lblTrangThai = new javax.swing.JLabel();
        pnlNoiDung_0 = new javax.swing.JPanel();
        pnlTrangChu = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        lblP1 = new javax.swing.JLabel();
        lblP2 = new javax.swing.JLabel();
        lblP3 = new javax.swing.JLabel();
        lblMore = new javax.swing.JLabel();
        lbl1So = new javax.swing.JLabel();
        lbl1 = new javax.swing.JLabel();
        lbl2So = new javax.swing.JLabel();
        lbl2 = new javax.swing.JLabel();
        lbl3So = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        pnlQLChuyenDe = new javax.swing.JPanel();
        pnlQLChuyenDe_2 = new javax.swing.JPanel();
        pnlQLChuyenDe_Head = new javax.swing.JPanel();
        lblQLChuyenDe_Title = new javax.swing.JLabel();
        lblQLChuyenDe_CapNhat = new javax.swing.JLabel();
        lblQLChuyenDe_DanhSach = new javax.swing.JLabel();
        pnlQLChuyenDe_Body = new javax.swing.JPanel();
        pnlQLChuyenDe_CapNhat = new javax.swing.JPanel();
        lbl2A_MaChuyenDe = new javax.swing.JLabel();
        txt2A_MaChuyenDe = new javax.swing.JTextField();
        lbl2A_TenChuyenDe = new javax.swing.JLabel();
        txt2A_TenChuyenDe = new javax.swing.JTextField();
        lbl2A_HocPhi = new javax.swing.JLabel();
        txt2A_HocPhi = new javax.swing.JTextField();
        lbl2A_ThoiLuong = new javax.swing.JLabel();
        txt2A_ThoiLuong = new javax.swing.JTextField();
        lbl2A_AnhDaiDien = new javax.swing.JLabel();
        lbl2A_MoTa = new javax.swing.JLabel();
        scr2A = new javax.swing.JScrollPane();
        txt2A_MoTa = new javax.swing.JTextArea();
        btn2A_Truoc = new javax.swing.JButton();
        btn2A_VeTruoc = new javax.swing.JButton();
        btn2A_VeSau = new javax.swing.JButton();
        btn2A_Sau = new javax.swing.JButton();
        btn2A_Them = new javax.swing.JButton();
        btn2A_Sua = new javax.swing.JButton();
        btn2A_Xoa = new javax.swing.JButton();
        btn2A_LamMoi = new javax.swing.JButton();
        pnlQLChuyenDe_DanhSach = new javax.swing.JPanel();
        lbl2B_TimKiem = new javax.swing.JLabel();
        txt2B_TimKiem = new javax.swing.JTextField();
        scr2B = new javax.swing.JScrollPane();
        tblQLChuyenDe = new javax.swing.JTable();
        pnlQLKhoaHoc = new javax.swing.JPanel();
        pnlQLKhoaHoc_3 = new javax.swing.JPanel();
        pnlQLKhoaHoc_Head = new javax.swing.JPanel();
        lblQLKhoaHoc_Title = new javax.swing.JLabel();
        lblQLKhoaHoc_CapNhat = new javax.swing.JLabel();
        lblQLKhoaHoc_DanhSach = new javax.swing.JLabel();
        pnlQLKhoaHoc_Body = new javax.swing.JPanel();
        pnlQLKhoaHoc_CapNhat = new javax.swing.JPanel();
        lbl3A_MaChuyenDe = new javax.swing.JLabel();
        txt3A_MaChuyenDe = new javax.swing.JTextField();
        lbl3A_TenChuyenDe = new javax.swing.JLabel();
        cbo3A_TenChuyenDe = new javax.swing.JComboBox<>();
        lbl3A_HocPhi = new javax.swing.JLabel();
        txt3A_HocPhi = new javax.swing.JTextField();
        lbl3A_ThoiLuong = new javax.swing.JLabel();
        txt3A_ThoiLuong = new javax.swing.JTextField();
        lbl3A_MaKhoaHoc = new javax.swing.JLabel();
        txt3A_MaKhoaHoc = new javax.swing.JTextField();
        lbl3A_NgayKhaiGiang = new javax.swing.JLabel();
        txt3A_NgayKhaiGiang = new javax.swing.JTextField();
        lbl3A_NgayTao = new javax.swing.JLabel();
        txt3A_NgayTao = new javax.swing.JTextField();
        lbl3A_GhiChu = new javax.swing.JLabel();
        scr3A = new javax.swing.JScrollPane();
        txt3A_GhiChu = new javax.swing.JTextArea();
        btn3A_Truoc = new javax.swing.JButton();
        btn3A_VeTruoc = new javax.swing.JButton();
        btn3A_VeSau = new javax.swing.JButton();
        btn3A_Sau = new javax.swing.JButton();
        btn3A_Them = new javax.swing.JButton();
        btn3A_Sua = new javax.swing.JButton();
        btn3A_Xoa = new javax.swing.JButton();
        btn3A_NhapMoi = new javax.swing.JButton();
        pnlQLKhoaHoc_DanhSach = new javax.swing.JPanel();
        lbl3B_TimKiem = new javax.swing.JLabel();
        txt3B_TimKiem = new javax.swing.JTextField();
        scr3B = new javax.swing.JScrollPane();
        tblQLKhoaHoc = new javax.swing.JTable();
        pnlQLNguoiHoc = new javax.swing.JPanel();
        pnlQLNguoiHoc_4 = new javax.swing.JPanel();
        pnlQLNguoiHoc_Head = new javax.swing.JPanel();
        lblQLNguoiHoc_Title = new javax.swing.JLabel();
        lblQLNguoiHoc_CapNhat = new javax.swing.JLabel();
        lblQLNguoiHoc_DanhSach = new javax.swing.JLabel();
        pnlQLNguoiHoc_Body = new javax.swing.JPanel();
        pnlQLNguoiHoc_CapNhat = new javax.swing.JPanel();
        lbl4A_MaNguoiHoc = new javax.swing.JLabel();
        txt4A_MaNguoiHoc = new javax.swing.JTextField();
        lbl4A_HoTen = new javax.swing.JLabel();
        txt4A_HoTen = new javax.swing.JTextField();
        lbl4A_GioiTinh = new javax.swing.JLabel();
        cbo4A_GioiTinh = new javax.swing.JComboBox<>();
        lbl4A_NgaySinh = new javax.swing.JLabel();
        txt4A_NgaySinh = new javax.swing.JTextField();
        lbl4A_SoDienThoai = new javax.swing.JLabel();
        txt4A_SoDienThoai = new javax.swing.JTextField();
        lbl4A_Email = new javax.swing.JLabel();
        txt4A_Email = new javax.swing.JTextField();
        lbl4A_DiaChi = new javax.swing.JLabel();
        txt4A_DiaChi = new javax.swing.JTextField();
        lbl4A_Anh = new javax.swing.JLabel();
        lbl4A_NgayDangKy = new javax.swing.JLabel();
        txt4A_NgayDangKy = new javax.swing.JTextField();
        lbl4A_GhiChu = new javax.swing.JLabel();
        scr4A = new javax.swing.JScrollPane();
        txt4A_GhiChu = new javax.swing.JTextArea();
        btn4A_Truoc = new javax.swing.JButton();
        btn4A_VeTruoc = new javax.swing.JButton();
        btn4A_VeSau = new javax.swing.JButton();
        btn4A_Sau = new javax.swing.JButton();
        btn4A_Them = new javax.swing.JButton();
        btn4A_Sua = new javax.swing.JButton();
        btn4A_Xoa = new javax.swing.JButton();
        btn4A_LamMoi = new javax.swing.JButton();
        pnlQLNguoiHoc_DanhSach = new javax.swing.JPanel();
        lbl4B_TimKiem = new javax.swing.JLabel();
        txt4B_TimKiem = new javax.swing.JTextField();
        scr4B = new javax.swing.JScrollPane();
        tblQLNguoiHoc = new javax.swing.JTable();
        pnlQLHocVien = new javax.swing.JPanel();
        pnlQLHocVien_5 = new javax.swing.JPanel();
        pnlQLHocVien_Head = new javax.swing.JPanel();
        lblQLHocVien_Title = new javax.swing.JLabel();
        lblQLHocVien_HocVien = new javax.swing.JLabel();
        lblQLHocVien_NguoiHoc = new javax.swing.JLabel();
        lblQLHocVien_ChuyenDe = new javax.swing.JLabel();
        cboQLHocVien_ChuyenDe = new javax.swing.JComboBox<>();
        lblQLHocVien_KhoaHoc = new javax.swing.JLabel();
        cboQLHocVien_KhoaHoc = new javax.swing.JComboBox<>();
        pnlQLHocVien_Body = new javax.swing.JPanel();
        pnlQLHocVien_HocVien = new javax.swing.JPanel();
        lbl5A_TimKiem = new javax.swing.JLabel();
        txt5A_TimKiem = new javax.swing.JTextField();
        scr5A = new javax.swing.JScrollPane();
        tblQLHocVien_HocVien = new javax.swing.JTable();
        btn5A_CapNhat = new javax.swing.JButton();
        btn5A_Xoa = new javax.swing.JButton();
        pnlQLHocVien_NguoiHoc = new javax.swing.JPanel();
        lbl5B_TimKiem = new javax.swing.JLabel();
        txt5B_TimKiem = new javax.swing.JTextField();
        scr5B = new javax.swing.JScrollPane();
        tblQLHocVien_NguoiHoc = new javax.swing.JTable();
        btn5B_Them = new javax.swing.JButton();
        pnlQLNhanVien = new javax.swing.JPanel();
        pnlQLNhanVien_6 = new javax.swing.JPanel();
        pnlQLNhanVien_Head = new javax.swing.JPanel();
        lblQLNhanVien_Title = new javax.swing.JLabel();
        lblQLNhanVien_CapNhat = new javax.swing.JLabel();
        lblQLNhanVien_DanhSach = new javax.swing.JLabel();
        pnlQLNhanVien_Body = new javax.swing.JPanel();
        pnlQLNhanVien_CapNhat = new javax.swing.JPanel();
        lbl6A_MaNhanVien = new javax.swing.JLabel();
        txt6A_MaNhanVien = new javax.swing.JTextField();
        lbl6A_HoTen = new javax.swing.JLabel();
        txt6A_HoTen = new javax.swing.JTextField();
        lbl6A_GioiTinh = new javax.swing.JLabel();
        cbo6A_GioiTinh = new javax.swing.JComboBox<>();
        lbl6A_NgaySinh = new javax.swing.JLabel();
        txt6A_NgaySinh = new javax.swing.JTextField();
        lbl6A_SoDienThoai = new javax.swing.JLabel();
        txt6A_SoDienThoai = new javax.swing.JTextField();
        lbl6A_Email = new javax.swing.JLabel();
        txt6A_Email = new javax.swing.JTextField();
        lbl6A_DiaChi = new javax.swing.JLabel();
        txt6A_DiaChi = new javax.swing.JTextField();
        lbl6A_Anh = new javax.swing.JLabel();
        lbl6A_CCCD = new javax.swing.JLabel();
        txt6A_CCCD = new javax.swing.JTextField();
        lbl6A_ChucVu = new javax.swing.JLabel();
        cbo6A_ChucVu = new javax.swing.JComboBox<>();
        lbl6A_MatKhau = new javax.swing.JLabel();
        pnl6A_MatKhau = new javax.swing.JPanel();
        lbl6A_HienMK = new javax.swing.JLabel();
        pwd6A_MatKhau = new javax.swing.JPasswordField();
        btn6A_Truoc = new javax.swing.JButton();
        btn6A_VeTruoc = new javax.swing.JButton();
        btn6A_VeSau = new javax.swing.JButton();
        btn6A_Sau = new javax.swing.JButton();
        btn6A_Them = new javax.swing.JButton();
        btn6A_Sua = new javax.swing.JButton();
        btn6A_Xoa = new javax.swing.JButton();
        btn6A_LamMoi = new javax.swing.JButton();
        pnlQLNhanVien_DanhSach = new javax.swing.JPanel();
        lbl6B_TimKiem = new javax.swing.JLabel();
        txt6B_TimKiem = new javax.swing.JTextField();
        src6B = new javax.swing.JScrollPane();
        tblQLNhanVien = new javax.swing.JTable();
        pnlTKTongHop = new javax.swing.JPanel();
        pnlTKTongHop_7 = new javax.swing.JPanel();
        pnlTKTongHop_Head = new javax.swing.JPanel();
        lblTKTongHop_Title = new javax.swing.JLabel();
        lblTK1 = new javax.swing.JLabel();
        lblTK2 = new javax.swing.JLabel();
        lblTK3 = new javax.swing.JLabel();
        lblTK4 = new javax.swing.JLabel();
        pnlTKTongHop_Body = new javax.swing.JPanel();
        pnlTK1 = new javax.swing.JPanel();
        lbl7A_ThoiGian = new javax.swing.JLabel();
        cbo7A_ThoiGian = new javax.swing.JComboBox<>();
        src7A = new javax.swing.JScrollPane();
        tblTKNguoiHoc = new javax.swing.JTable();
        btn7A_Xuat = new javax.swing.JButton();
        pnlTK2 = new javax.swing.JPanel();
        lbl7B_ChuyenDe = new javax.swing.JLabel();
        cbo7B_ChuyenDe = new javax.swing.JComboBox<>();
        lbl7B_KhoaHoc = new javax.swing.JLabel();
        cbo7B_KhoaHoc = new javax.swing.JComboBox<>();
        src7B = new javax.swing.JScrollPane();
        tblTKDiem_KhoaHoc = new javax.swing.JTable();
        btn7B_Xuat = new javax.swing.JButton();
        pnlTK3 = new javax.swing.JPanel();
        lbl7C_Nam = new javax.swing.JLabel();
        cbo7C_Nam = new javax.swing.JComboBox<>();
        scr7C = new javax.swing.JScrollPane();
        tblTKDiem_ChuyenDe = new javax.swing.JTable();
        btn7C_Xuat = new javax.swing.JButton();
        pnlTK4 = new javax.swing.JPanel();
        lbl7D_Nam = new javax.swing.JLabel();
        cbo7D_Nam = new javax.swing.JComboBox<>();
        scr7D = new javax.swing.JScrollPane();
        tblTKDoanhThu_ChuyenDe = new javax.swing.JTable();
        btn7D_Xuat = new javax.swing.JButton();
        lblAnhNen = new javax.swing.JLabel();
        mnubar = new javax.swing.JMenuBar();
        mnuHeThong = new javax.swing.JMenu();
        mniDoiMatKhau = new javax.swing.JMenuItem();
        sprA1 = new javax.swing.JPopupMenu.Separator();
        mniDangXuat = new javax.swing.JMenuItem();
        mniThoat = new javax.swing.JMenuItem();
        mnuQuanLy = new javax.swing.JMenu();
        mniQLChuyenDe = new javax.swing.JMenuItem();
        mniQLKhHoc = new javax.swing.JMenuItem();
        sprB1 = new javax.swing.JPopupMenu.Separator();
        mniQLNgHoc = new javax.swing.JMenuItem();
        mniQLHoVien = new javax.swing.JMenuItem();
        sprB2 = new javax.swing.JPopupMenu.Separator();
        mniNhVien = new javax.swing.JMenuItem();
        mnuThongKe = new javax.swing.JMenu();
        mniTK1 = new javax.swing.JMenuItem();
        mniTK2 = new javax.swing.JMenuItem();
        mniTK3 = new javax.swing.JMenuItem();
        mniTK4 = new javax.swing.JMenuItem();
        mnuTroGiup = new javax.swing.JMenu();
        mniHuongDan = new javax.swing.JMenuItem();
        mniGioiThieu = new javax.swing.JMenuItem();
        mniBaoCao = new javax.swing.JMenuItem();

        JDialog_ChaoMung.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JDialog_ChaoMung.setTitle("HỆ THỐNG QUẢN LÝ ĐÀO TẠO - WOLVESEDU");
        JDialog_ChaoMung.setIconImage(null);
        JDialog_ChaoMung.setMinimumSize(new java.awt.Dimension(964, 380));
        JDialog_ChaoMung.setSize(new java.awt.Dimension(964, 380));

        pnlA1.setBackground(new java.awt.Color(244, 248, 248));
        pnlA1.setPreferredSize(new java.awt.Dimension(964, 380));
        pnlA1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        prgA1.setBackground(new java.awt.Color(74, 233, 251));
        prgA1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        prgA1.setForeground(new java.awt.Color(0, 255, 0));
        prgA1.setValue(37);
        prgA1.setOpaque(true);
        prgA1.setStringPainted(true);
        pnlA1.add(prgA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 244, 740, 30));

        lblA1.setIcon(new ImageIcon(ImageHelper.anhChuan(new ImageIcon("image/UI/logo.png").getImage(), 210, 210))
        );
        pnlA1.add(lblA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 210, 210));

        javax.swing.GroupLayout JDialog_ChaoMungLayout = new javax.swing.GroupLayout(JDialog_ChaoMung.getContentPane());
        JDialog_ChaoMung.getContentPane().setLayout(JDialog_ChaoMungLayout);
        JDialog_ChaoMungLayout.setHorizontalGroup(
            JDialog_ChaoMungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlA1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JDialog_ChaoMungLayout.setVerticalGroup(
            JDialog_ChaoMungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlA1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        JFrame_DangNhap.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JFrame_DangNhap.setTitle("ĐĂNG NHẬP");
        JFrame_DangNhap.setMinimumSize(new java.awt.Dimension(964, 380));
        JFrame_DangNhap.setResizable(false);
        JFrame_DangNhap.setSize(new java.awt.Dimension(964, 380));

        pnlB1.setBackground(new java.awt.Color(244, 248, 248));
        pnlB1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pwdB1_MatKhau.setBackground(new java.awt.Color(244, 248, 248));
        pwdB1_MatKhau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pwdB1_MatKhau.setBorder(null);
        pwdB1_MatKhau.setOpaque(false);
        pwdB1_MatKhau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pwdB1_MatKhauKeyReleased(evt);
            }
        });
        pnlB1.add(pwdB1_MatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 172, 250, 30));

        txtB1_TaiKhoan.setBackground(new java.awt.Color(244, 248, 248));
        txtB1_TaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtB1_TaiKhoan.setBorder(null);
        txtB1_TaiKhoan.setOpaque(false);
        pnlB1.add(txtB1_TaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 109, 280, 30));

        lblB1_HienMK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB1_HienMK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/lblAn.PNG"))); // NOI18N
        lblB1_HienMK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblB1_HienMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblB1_HienMKMouseClicked(evt);
            }
        });
        pnlB1.add(lblB1_HienMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 172, 36, 30));

        chkB1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        chkB1.setText(" Ghi nhớ tôi");
        chkB1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkB1.setOpaque(false);
        chkB1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkB1MouseClicked(evt);
            }
        });
        pnlB1.add(chkB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 230, -1, -1));

        lblB1_QuenMatKhau.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblB1_QuenMatKhau.setForeground(new java.awt.Color(74, 211, 252));
        lblB1_QuenMatKhau.setText("Quên mật khẩu?");
        lblB1_QuenMatKhau.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblB1_QuenMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblB1_QuenMatKhauMouseClicked(evt);
            }
        });
        pnlB1.add(lblB1_QuenMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 230, -1, -1));

        btnB1.setBackground(new java.awt.Color(74, 233, 251));
        btnB1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnB1.setForeground(new java.awt.Color(255, 255, 255));
        btnB1.setText("Đăng nhập");
        btnB1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnB1ActionPerformed(evt);
            }
        });
        pnlB1.add(btnB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 270, 180, 50));

        lblB1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/bkgB1.JPG"))); // NOI18N
        pnlB1.add(lblB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout JFrame_DangNhapLayout = new javax.swing.GroupLayout(JFrame_DangNhap.getContentPane());
        JFrame_DangNhap.getContentPane().setLayout(JFrame_DangNhapLayout);
        JFrame_DangNhapLayout.setHorizontalGroup(
            JFrame_DangNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JFrame_DangNhapLayout.setVerticalGroup(
            JFrame_DangNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlB1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );

        JFrame_QuenMatKhau.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JFrame_QuenMatKhau.setTitle("QUÊN MẬT KHẨU");
        JFrame_QuenMatKhau.setBackground(new java.awt.Color(244, 248, 248));
        JFrame_QuenMatKhau.setMinimumSize(new java.awt.Dimension(965, 467));
        JFrame_QuenMatKhau.setSize(new java.awt.Dimension(965, 467));

        pnlC1.setBackground(new java.awt.Color(244, 248, 248));
        pnlC1.setPreferredSize(new java.awt.Dimension(965, 467));
        pnlC1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtC1_TaiKhoan.setBackground(new java.awt.Color(244, 248, 248));
        txtC1_TaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtC1_TaiKhoan.setBorder(null);
        txtC1_TaiKhoan.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtC1_TaiKhoan.setOpaque(false);
        pnlC1.add(txtC1_TaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 280, 30));

        txtC1_Email.setBackground(new java.awt.Color(244, 248, 248));
        txtC1_Email.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtC1_Email.setBorder(null);
        txtC1_Email.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtC1_Email.setOpaque(false);
        pnlC1.add(txtC1_Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 188, 280, 30));

        txtC1_MaXacNhan.setEditable(false);
        txtC1_MaXacNhan.setBackground(new java.awt.Color(244, 248, 248));
        txtC1_MaXacNhan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtC1_MaXacNhan.setBorder(null);
        txtC1_MaXacNhan.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtC1_MaXacNhan.setOpaque(false);
        pnlC1.add(txtC1_MaXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 245, 280, 30));

        pwdC1_MatKhauMoi.setEditable(false);
        pwdC1_MatKhauMoi.setBackground(new java.awt.Color(244, 248, 248));
        pwdC1_MatKhauMoi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pwdC1_MatKhauMoi.setBorder(null);
        pwdC1_MatKhauMoi.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pwdC1_MatKhauMoi.setOpaque(false);
        pnlC1.add(pwdC1_MatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 143, 250, 30));

        pwdC1_XacNhan.setEditable(false);
        pwdC1_XacNhan.setBackground(new java.awt.Color(244, 248, 248));
        pwdC1_XacNhan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pwdC1_XacNhan.setBorder(null);
        pwdC1_XacNhan.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pwdC1_XacNhan.setOpaque(false);
        pnlC1.add(pwdC1_XacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 245, 250, 30));

        btnC1_GuiMaXacNhan.setIcon(new ImageIcon(ImageHelper.anhChuan(new ImageIcon("image/icon/gui.png").getImage(), 28, 28))
        );
        btnC1_GuiMaXacNhan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnC1_GuiMaXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnC1_GuiMaXacNhanActionPerformed(evt);
            }
        });
        pnlC1.add(btnC1_GuiMaXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 190, -1, -1));

        lblC1_HienMKM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC1_HienMKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/lblAn.PNG"))); // NOI18N
        lblC1_HienMKM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblC1_HienMKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblC1_HienMKMMouseClicked(evt);
            }
        });
        pnlC1.add(lblC1_HienMKM, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 143, 30, 30));

        lblC1_HienXN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC1_HienXN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/lblAn.PNG"))); // NOI18N
        lblC1_HienXN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblC1_HienXN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblC1_HienXNMouseClicked(evt);
            }
        });
        pnlC1.add(lblC1_HienXN, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 245, 30, 30));

        btnC1.setBackground(new java.awt.Color(74, 233, 251));
        btnC1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnC1.setForeground(new java.awt.Color(255, 255, 255));
        btnC1.setText("Lấy lại mật khẩu");
        btnC1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnC1.setEnabled(false);
        btnC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnC1ActionPerformed(evt);
            }
        });
        pnlC1.add(btnC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 318, 230, 62));

        lblC1_ClearQMK.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblC1_ClearQMK.setForeground(new java.awt.Color(0, 0, 255));
        lblC1_ClearQMK.setText("Nhập lại...");
        lblC1_ClearQMK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblC1_ClearQMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblC1_ClearQMKMouseClicked(evt);
            }
        });
        pnlC1.add(lblC1_ClearQMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, -1, -1));

        lblC1_QuayLai.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblC1_QuayLai.setForeground(new java.awt.Color(0, 0, 255));
        lblC1_QuayLai.setText("Quay lại đăng nhập...");
        lblC1_QuayLai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblC1_QuayLai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblC1_QuayLaiMouseClicked(evt);
            }
        });
        pnlC1.add(lblC1_QuayLai, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 350, -1, -1));

        lblC1_DangGui.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblC1_DangGui.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC1_DangGui.setText("...");
        pnlC1.add(lblC1_DangGui, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 250, 50, -1));

        lblC1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/bkgC1.JPG"))); // NOI18N
        pnlC1.add(lblC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 420));

        javax.swing.GroupLayout JFrame_QuenMatKhauLayout = new javax.swing.GroupLayout(JFrame_QuenMatKhau.getContentPane());
        JFrame_QuenMatKhau.getContentPane().setLayout(JFrame_QuenMatKhauLayout);
        JFrame_QuenMatKhauLayout.setHorizontalGroup(
            JFrame_QuenMatKhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlC1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JFrame_QuenMatKhauLayout.setVerticalGroup(
            JFrame_QuenMatKhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlC1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        JFrame_DoiMatKhau.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JFrame_DoiMatKhau.setTitle("ĐỔI MẬT KHẨU");
        JFrame_DoiMatKhau.setBackground(new java.awt.Color(244, 248, 248));
        JFrame_DoiMatKhau.setResizable(false);
        JFrame_DoiMatKhau.setSize(new java.awt.Dimension(965, 467));

        pnlD1.setBackground(new java.awt.Color(244, 248, 248));
        pnlD1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblD1_HienMKHT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD1_HienMKHT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/lblAn.PNG"))); // NOI18N
        lblD1_HienMKHT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblD1_HienMKHT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblD1_HienMKHTMouseClicked(evt);
            }
        });
        pnlD1.add(lblD1_HienMKHT, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 134, 40, 30));

        pwdD1_MatKhauHienTai.setBackground(new java.awt.Color(244, 248, 248));
        pwdD1_MatKhauHienTai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pwdD1_MatKhauHienTai.setBorder(null);
        pwdD1_MatKhauHienTai.setOpaque(false);
        pnlD1.add(pwdD1_MatKhauHienTai, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 134, 350, 30));

        lblD1_HienMKM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD1_HienMKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/lblAn.PNG"))); // NOI18N
        lblD1_HienMKM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblD1_HienMKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblD1_HienMKMMouseClicked(evt);
            }
        });
        pnlD1.add(lblD1_HienMKM, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 192, 40, 30));

        pwdD1_MatKhauMoi.setBackground(new java.awt.Color(244, 248, 248));
        pwdD1_MatKhauMoi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pwdD1_MatKhauMoi.setBorder(null);
        pwdD1_MatKhauMoi.setOpaque(false);
        pnlD1.add(pwdD1_MatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 192, 350, 30));

        lblD1_HienXN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD1_HienXN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/lblAn.PNG"))); // NOI18N
        lblD1_HienXN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblD1_HienXN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblD1_HienXNMouseClicked(evt);
            }
        });
        pnlD1.add(lblD1_HienXN, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 249, 40, 30));

        pwdD1_XacNhan.setBackground(new java.awt.Color(244, 248, 248));
        pwdD1_XacNhan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pwdD1_XacNhan.setBorder(null);
        pwdD1_XacNhan.setOpaque(false);
        pnlD1.add(pwdD1_XacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 249, 350, 30));

        btnD1.setBackground(new java.awt.Color(74, 233, 251));
        btnD1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnD1.setForeground(new java.awt.Color(255, 255, 255));
        btnD1.setText("Đổi mật khẩu");
        btnD1.setToolTipText("");
        btnD1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnD1ActionPerformed(evt);
            }
        });
        pnlD1.add(btnD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 322, 230, 60));

        lblD1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/bkgD1.JPG"))); // NOI18N
        pnlD1.add(lblD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout JFrame_DoiMatKhauLayout = new javax.swing.GroupLayout(JFrame_DoiMatKhau.getContentPane());
        JFrame_DoiMatKhau.getContentPane().setLayout(JFrame_DoiMatKhauLayout);
        JFrame_DoiMatKhauLayout.setHorizontalGroup(
            JFrame_DoiMatKhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlD1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JFrame_DoiMatKhauLayout.setVerticalGroup(
            JFrame_DoiMatKhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlD1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        JFrame_HuongDan.setTitle("HƯỚNG DẪN SỬ DỤNG");
        JFrame_HuongDan.setResizable(false);
        JFrame_HuongDan.setSize(new java.awt.Dimension(1025, 680));

        pnlE0.setBackground(new java.awt.Color(0, 0, 0));

        lblE1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblE1.setForeground(new java.awt.Color(0, 153, 255));
        lblE1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE1.setText("HƯỚNG DẪN SỬ DỤNG");

        pnlE1.setBackground(new java.awt.Color(0, 0, 0));

        btnE1_DangNhap.setBackground(new java.awt.Color(153, 255, 255));
        btnE1_DangNhap.setText("Đăng nhập");
        btnE1_DangNhap.setPreferredSize(new java.awt.Dimension(95, 40));
        btnE1_DangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnE1_DangNhapActionPerformed(evt);
            }
        });
        pnlE1.add(btnE1_DangNhap);

        btnE1_QuenMatKhau.setBackground(new java.awt.Color(153, 255, 255));
        btnE1_QuenMatKhau.setText("Quên mật khẩu");
        btnE1_QuenMatKhau.setPreferredSize(new java.awt.Dimension(119, 40));
        btnE1_QuenMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnE1_QuenMatKhauActionPerformed(evt);
            }
        });
        pnlE1.add(btnE1_QuenMatKhau);

        btnE1_DoiMatKhau.setBackground(new java.awt.Color(153, 255, 255));
        btnE1_DoiMatKhau.setText("Đổi mật khẩu");
        btnE1_DoiMatKhau.setPreferredSize(new java.awt.Dimension(109, 40));
        btnE1_DoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnE1_DoiMatKhauActionPerformed(evt);
            }
        });
        pnlE1.add(btnE1_DoiMatKhau);

        btnE1_ChuyenDe.setBackground(new java.awt.Color(153, 255, 255));
        btnE1_ChuyenDe.setText("Chuyên đề");
        btnE1_ChuyenDe.setPreferredSize(new java.awt.Dimension(93, 40));
        btnE1_ChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnE1_ChuyenDeActionPerformed(evt);
            }
        });
        pnlE1.add(btnE1_ChuyenDe);

        btnE1_KhoaHoc.setBackground(new java.awt.Color(204, 204, 204));
        btnE1_KhoaHoc.setText("Khóa học");
        btnE1_KhoaHoc.setPreferredSize(new java.awt.Dimension(85, 40));
        btnE1_KhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnE1_KhoaHocActionPerformed(evt);
            }
        });
        pnlE1.add(btnE1_KhoaHoc);

        btnE1_NguoiHoc.setBackground(new java.awt.Color(204, 204, 204));
        btnE1_NguoiHoc.setText("Người học");
        btnE1_NguoiHoc.setPreferredSize(new java.awt.Dimension(89, 40));
        btnE1_NguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnE1_NguoiHocActionPerformed(evt);
            }
        });
        pnlE1.add(btnE1_NguoiHoc);

        btnE1_HocVien.setBackground(new java.awt.Color(204, 204, 204));
        btnE1_HocVien.setText("Học viên");
        btnE1_HocVien.setPreferredSize(new java.awt.Dimension(81, 40));
        btnE1_HocVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnE1_HocVienActionPerformed(evt);
            }
        });
        pnlE1.add(btnE1_HocVien);

        btnE1_NhanVien.setBackground(new java.awt.Color(204, 204, 204));
        btnE1_NhanVien.setText("Nhân viên");
        btnE1_NhanVien.setPreferredSize(new java.awt.Dimension(89, 40));
        btnE1_NhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnE1_NhanVienActionPerformed(evt);
            }
        });
        pnlE1.add(btnE1_NhanVien);

        btnE1_ThongKe.setBackground(new java.awt.Color(153, 255, 255));
        btnE1_ThongKe.setText("Thống kê");
        btnE1_ThongKe.setMinimumSize(new java.awt.Dimension(85, 40));
        btnE1_ThongKe.setPreferredSize(new java.awt.Dimension(85, 40));
        btnE1_ThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnE1_ThongKeActionPerformed(evt);
            }
        });
        pnlE1.add(btnE1_ThongKe);

        pnlE1_video.setBackground(new java.awt.Color(0, 0, 0));
        pnlE1_video.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout pnlE1_videoLayout = new javax.swing.GroupLayout(pnlE1_video);
        pnlE1_video.setLayout(pnlE1_videoLayout);
        pnlE1_videoLayout.setHorizontalGroup(
            pnlE1_videoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlE1_videoLayout.setVerticalGroup(
            pnlE1_videoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 526, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlE0Layout = new javax.swing.GroupLayout(pnlE0);
        pnlE0.setLayout(pnlE0Layout);
        pnlE0Layout.setHorizontalGroup(
            pnlE0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlE0Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlE0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblE1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlE1, javax.swing.GroupLayout.DEFAULT_SIZE, 1001, Short.MAX_VALUE)
                    .addComponent(pnlE1_video, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlE0Layout.setVerticalGroup(
            pnlE0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlE0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblE1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlE1_video, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlE1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout JFrame_HuongDanLayout = new javax.swing.GroupLayout(JFrame_HuongDan.getContentPane());
        JFrame_HuongDan.getContentPane().setLayout(JFrame_HuongDanLayout);
        JFrame_HuongDanLayout.setHorizontalGroup(
            JFrame_HuongDanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlE0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JFrame_HuongDanLayout.setVerticalGroup(
            JFrame_HuongDanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlE0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        JDialog_GioiThieu.setTitle("GIỚI THIỆU");
        JDialog_GioiThieu.setBackground(new java.awt.Color(244, 248, 248));
        JDialog_GioiThieu.setMinimumSize(new java.awt.Dimension(1200, 720));
        JDialog_GioiThieu.setResizable(false);
        JDialog_GioiThieu.setSize(new java.awt.Dimension(1200, 720));

        pnlF1.setBackground(new java.awt.Color(244, 248, 248));
        pnlF1.setMinimumSize(new java.awt.Dimension(1200, 720));
        pnlF1.setPreferredSize(new java.awt.Dimension(1200, 720));
        pnlF1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblF1_p1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pnlF1.add(lblF1_p1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, 960, 70));

        lblF1_p2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pnlF1.add(lblF1_p2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 960, 200));

        lblF1_DiaChi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblF1_DiaChi.setForeground(new java.awt.Color(0, 0, 255));
        lblF1_DiaChi.setText("Địa chỉ: 288 Nguyễn Văn Linh, P. An Khánh, Q. Ninh Kiều, TP. Cần Thơ");
        lblF1_DiaChi.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pnlF1.add(lblF1_DiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 610, -1, -1));

        lblF1_LienHe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblF1_LienHe.setForeground(new java.awt.Color(0, 0, 255));
        lblF1_LienHe.setText("Liện hệ: 0969989987");
        lblF1_LienHe.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pnlF1.add(lblF1_LienHe, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 610, -1, -1));

        lblF1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/bkgF1.jpg"))); // NOI18N
        lblF1.setPreferredSize(new java.awt.Dimension(1200, 653));
        pnlF1.add(lblF1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, -1));

        javax.swing.GroupLayout JDialog_GioiThieuLayout = new javax.swing.GroupLayout(JDialog_GioiThieu.getContentPane());
        JDialog_GioiThieu.getContentPane().setLayout(JDialog_GioiThieuLayout);
        JDialog_GioiThieuLayout.setHorizontalGroup(
            JDialog_GioiThieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlF1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JDialog_GioiThieuLayout.setVerticalGroup(
            JDialog_GioiThieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlF1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        JFrame_BaoCao.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        JFrame_BaoCao.setTitle("BÁO CÁO");
        JFrame_BaoCao.setResizable(false);
        JFrame_BaoCao.setSize(new java.awt.Dimension(700, 340));

        pnlA2.setBackground(new java.awt.Color(255, 255, 255));
        pnlA2.setMinimumSize(new java.awt.Dimension(700, 340));
        pnlA2.setPreferredSize(new java.awt.Dimension(700, 340));

        lblG1_BaoCao.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblG1_BaoCao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG1_BaoCao.setText("BÁO CÁO SỰ CỐ");

        lblG1_NoiDung.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblG1_NoiDung.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblG1_NoiDung.setText("Nội dung");

        txtG1_NoiDung.setColumns(20);
        txtG1_NoiDung.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtG1_NoiDung.setRows(3);
        srcG1.setViewportView(txtG1_NoiDung);

        lblG1_TieuDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblG1_TieuDe.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblG1_TieuDe.setText("Tiêu đề");

        txtG1_TieuDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnG1_ChonAnh.setBackground(new java.awt.Color(74, 233, 251));
        btnG1_ChonAnh.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnG1_ChonAnh.setForeground(new java.awt.Color(255, 255, 255));
        btnG1_ChonAnh.setText("Chọn ảnh");
        btnG1_ChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnG1_ChonAnhActionPerformed(evt);
            }
        });

        btnG1_Gui.setBackground(new java.awt.Color(74, 233, 251));
        btnG1_Gui.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnG1_Gui.setForeground(new java.awt.Color(255, 255, 255));
        btnG1_Gui.setText("Gửi");
        btnG1_Gui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnG1_GuiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlA2Layout = new javax.swing.GroupLayout(pnlA2);
        pnlA2.setLayout(pnlA2Layout);
        pnlA2Layout.setHorizontalGroup(
            pnlA2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlA2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlA2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblG1_BaoCao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlA2Layout.createSequentialGroup()
                        .addGroup(pnlA2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlA2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnG1_ChonAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnG1_Gui, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlA2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(pnlA2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblG1_TieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblG1_NoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlA2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(srcG1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                                    .addComponent(txtG1_TieuDe))))
                        .addGap(148, 148, 148)))
                .addContainerGap())
        );
        pnlA2Layout.setVerticalGroup(
            pnlA2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlA2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblG1_BaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlA2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtG1_TieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblG1_TieuDe))
                .addGap(18, 18, 18)
                .addGroup(pnlA2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(srcG1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblG1_NoiDung))
                .addGap(18, 18, 18)
                .addGroup(pnlA2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnG1_Gui, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnG1_ChonAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout JFrame_BaoCaoLayout = new javax.swing.GroupLayout(JFrame_BaoCao.getContentPane());
        JFrame_BaoCao.getContentPane().setLayout(JFrame_BaoCaoLayout);
        JFrame_BaoCaoLayout.setHorizontalGroup(
            JFrame_BaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlA2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JFrame_BaoCaoLayout.setVerticalGroup(
            JFrame_BaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlA2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HỆ THỐNG QUẢN LÝ ĐÀO TẠO - WOLVESEDU");
        setResizable(false);

        pnlDieuHuong.setBackground(new java.awt.Color(255, 255, 255));

        pnlAnhDaiDien.setPreferredSize(new java.awt.Dimension(247, 247));
        pnlAnhDaiDien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMaNhanVien.setBackground(new java.awt.Color(74, 233, 251));
        lblMaNhanVien.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMaNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lblMaNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMaNhanVien.setText("NV01");
        lblMaNhanVien.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 2, 1, 2, new java.awt.Color(0, 0, 0)));
        lblMaNhanVien.setOpaque(true);
        pnlAnhDaiDien.add(lblMaNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 70, 40));

        lblAnhDaiDien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnhDaiDien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lblAnhDaiDien.setPreferredSize(new java.awt.Dimension(280, 280));
        pnlAnhDaiDien.add(lblAnhDaiDien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 276, 276));

        pnlMenu.setBackground(new java.awt.Color(255, 255, 255));
        pnlMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTrangChu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTrangChu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/72x36/trangchu.png"))); // NOI18N
        lblTrangChu.setText("  Trang chủ");
        lblTrangChu.setOpaque(true);
        lblTrangChu.setPreferredSize(new java.awt.Dimension(41, 63));
        lblTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTrangChuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTrangChuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTrangChuMouseExited(evt);
            }
        });
        pnlMenu.add(lblTrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 276, -1));

        lblQLChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLChuyenDe.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblQLChuyenDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/72x36/chuyende.png"))); // NOI18N
        lblQLChuyenDe.setText("  Quản lý chuyên đề");
        lblQLChuyenDe.setOpaque(true);
        lblQLChuyenDe.setPreferredSize(new java.awt.Dimension(41, 63));
        lblQLChuyenDe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLChuyenDeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLChuyenDeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLChuyenDeMouseExited(evt);
            }
        });
        pnlMenu.add(lblQLChuyenDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 63, 276, -1));

        lblQLKhoaHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLKhoaHoc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblQLKhoaHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/72x36/khoahoc.png"))); // NOI18N
        lblQLKhoaHoc.setText("  Quản lý khóa học");
        lblQLKhoaHoc.setOpaque(true);
        lblQLKhoaHoc.setPreferredSize(new java.awt.Dimension(41, 63));
        lblQLKhoaHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLKhoaHocMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLKhoaHocMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLKhoaHocMouseExited(evt);
            }
        });
        pnlMenu.add(lblQLKhoaHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 126, 276, -1));

        lblQLNguoiHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLNguoiHoc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblQLNguoiHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/72x36/nguoihoc.png"))); // NOI18N
        lblQLNguoiHoc.setText("  Quản lý người học");
        lblQLNguoiHoc.setOpaque(true);
        lblQLNguoiHoc.setPreferredSize(new java.awt.Dimension(41, 63));
        lblQLNguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLNguoiHocMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLNguoiHocMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLNguoiHocMouseExited(evt);
            }
        });
        pnlMenu.add(lblQLNguoiHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 189, 276, -1));

        lblQLHocVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLHocVien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblQLHocVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/72x36/hocvien.png"))); // NOI18N
        lblQLHocVien.setText("  Quản lý học viên");
        lblQLHocVien.setOpaque(true);
        lblQLHocVien.setPreferredSize(new java.awt.Dimension(41, 63));
        lblQLHocVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLHocVienMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLHocVienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLHocVienMouseExited(evt);
            }
        });
        pnlMenu.add(lblQLHocVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 252, 276, -1));

        lblQLNhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblQLNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/72x36/nhanvien.png"))); // NOI18N
        lblQLNhanVien.setText("  Quản lý nhân viên");
        lblQLNhanVien.setOpaque(true);
        lblQLNhanVien.setPreferredSize(new java.awt.Dimension(41, 63));
        lblQLNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLNhanVienMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLNhanVienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLNhanVienMouseExited(evt);
            }
        });
        pnlMenu.add(lblQLNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 315, 276, -1));

        lblTKTongHop.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTKTongHop.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTKTongHop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/72x36/thongke.png"))); // NOI18N
        lblTKTongHop.setText("  Thống kê tổng hợp");
        lblTKTongHop.setOpaque(true);
        lblTKTongHop.setPreferredSize(new java.awt.Dimension(41, 63));
        lblTKTongHop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTKTongHopMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTKTongHopMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTKTongHopMouseExited(evt);
            }
        });
        pnlMenu.add(lblTKTongHop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 378, 276, -1));

        lblBaoCao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblBaoCao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBaoCao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/72x36/baocao.png"))); // NOI18N
        lblBaoCao.setText("  Báo cáo");
        lblBaoCao.setOpaque(true);
        lblBaoCao.setPreferredSize(new java.awt.Dimension(41, 63));
        lblBaoCao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBaoCaoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBaoCaoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblBaoCaoMouseExited(evt);
            }
        });
        pnlMenu.add(lblBaoCao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 441, 276, -1));

        lblDangXuat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDangXuat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/72x36/dangxuat.png"))); // NOI18N
        lblDangXuat.setText("  Đăng xuất");
        lblDangXuat.setOpaque(true);
        lblDangXuat.setPreferredSize(new java.awt.Dimension(41, 63));
        lblDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangXuatMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDangXuatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDangXuatMouseExited(evt);
            }
        });
        pnlMenu.add(lblDangXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 504, 276, -1));

        javax.swing.GroupLayout pnlDieuHuongLayout = new javax.swing.GroupLayout(pnlDieuHuong);
        pnlDieuHuong.setLayout(pnlDieuHuongLayout);
        pnlDieuHuongLayout.setHorizontalGroup(
            pnlDieuHuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDieuHuongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDieuHuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAnhDaiDien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlDieuHuongLayout.setVerticalGroup(
            pnlDieuHuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDieuHuongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlAnhDaiDien, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlNoiDung.setBackground(new java.awt.Color(255, 255, 255));
        pnlNoiDung.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlTrangThai.setOpaque(false);

        lblTrangThai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTrangThai.setText("Trang chủ");

        javax.swing.GroupLayout pnlTrangThaiLayout = new javax.swing.GroupLayout(pnlTrangThai);
        pnlTrangThai.setLayout(pnlTrangThaiLayout);
        pnlTrangThaiLayout.setHorizontalGroup(
            pnlTrangThaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTrangThaiLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(lblTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(948, Short.MAX_VALUE))
        );
        pnlTrangThaiLayout.setVerticalGroup(
            pnlTrangThaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTrangThaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlNoiDung.add(pnlTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 70));

        pnlNoiDung_0.setOpaque(false);
        pnlNoiDung_0.setLayout(new java.awt.CardLayout());

        pnlTrangChu.setOpaque(false);
        pnlTrangChu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblWelcome.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblWelcome.setText("Welcome, NV01");
        pnlTrangChu.add(lblWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 52, -1, -1));

        lblP1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblP1.setText("Hệ thống quản lý các khóa học ngắn hạn của công ty lập trình Wolvesedu được xây dựng");
        pnlTrangChu.add(lblP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 94, -1, -1));

        lblP2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblP2.setText("rất chặt chẽ, nhanh chóng và thông minh. Phần mềm quản lý giúp cho việc kiểm soát");
        pnlTrangChu.add(lblP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 136, -1, -1));

        lblP3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblP3.setText("và truy xuất của quản lý tiết kiệm được thời gian và bảo mật hơn cùng với sự...");
        pnlTrangChu.add(lblP3, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 178, -1, -1));

        lblMore.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblMore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/lblMore.PNG"))); // NOI18N
        lblMore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMoreMouseClicked(evt);
            }
        });
        pnlTrangChu.add(lblMore, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 220, -1, -1));

        lbl1So.setFont(new java.awt.Font("Tahoma", 1, 77)); // NOI18N
        lbl1So.setForeground(new java.awt.Color(255, 255, 255));
        lbl1So.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl1So.setText("999");
        pnlTrangChu.add(lbl1So, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 300, 90));

        lbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/lbl1.png"))); // NOI18N
        pnlTrangChu.add(lbl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 340, -1, -1));

        lbl2So.setFont(new java.awt.Font("Tahoma", 1, 77)); // NOI18N
        lbl2So.setForeground(new java.awt.Color(255, 255, 255));
        lbl2So.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl2So.setText("999");
        pnlTrangChu.add(lbl2So, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 500, 290, 90));

        lbl2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/lbl2.png"))); // NOI18N
        pnlTrangChu.add(lbl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(516, 341, -1, -1));

        lbl3So.setFont(new java.awt.Font("Tahoma", 1, 77)); // NOI18N
        lbl3So.setForeground(new java.awt.Color(255, 255, 255));
        lbl3So.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl3So.setText("999");
        pnlTrangChu.add(lbl3So, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 500, 300, 90));

        lbl3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/lbl3.png"))); // NOI18N
        pnlTrangChu.add(lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 340, -1, -1));

        pnlNoiDung_0.add(pnlTrangChu, "trangchu");

        pnlQLChuyenDe.setBackground(new java.awt.Color(255, 255, 204));
        pnlQLChuyenDe.setOpaque(false);

        pnlQLChuyenDe_2.setOpaque(false);

        pnlQLChuyenDe_Head.setOpaque(false);

        lblQLChuyenDe_Title.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblQLChuyenDe_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLChuyenDe_Title.setText("QUẢN LÝ CHUYÊN ĐỀ");

        lblQLChuyenDe_CapNhat.setBackground(new java.awt.Color(242, 242, 242));
        lblQLChuyenDe_CapNhat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLChuyenDe_CapNhat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLChuyenDe_CapNhat.setText("Cập nhật");
        lblQLChuyenDe_CapNhat.setOpaque(true);
        lblQLChuyenDe_CapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLChuyenDe_CapNhatMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLChuyenDe_CapNhatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLChuyenDe_CapNhatMouseExited(evt);
            }
        });

        lblQLChuyenDe_DanhSach.setBackground(new java.awt.Color(242, 242, 242));
        lblQLChuyenDe_DanhSach.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLChuyenDe_DanhSach.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLChuyenDe_DanhSach.setText("Danh sách");
        lblQLChuyenDe_DanhSach.setOpaque(true);
        lblQLChuyenDe_DanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLChuyenDe_DanhSachMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLChuyenDe_DanhSachMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLChuyenDe_DanhSachMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlQLChuyenDe_HeadLayout = new javax.swing.GroupLayout(pnlQLChuyenDe_Head);
        pnlQLChuyenDe_Head.setLayout(pnlQLChuyenDe_HeadLayout);
        pnlQLChuyenDe_HeadLayout.setHorizontalGroup(
            pnlQLChuyenDe_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLChuyenDe_HeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLChuyenDe_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlQLChuyenDe_HeadLayout.createSequentialGroup()
                .addComponent(lblQLChuyenDe_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblQLChuyenDe_DanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlQLChuyenDe_HeadLayout.setVerticalGroup(
            pnlQLChuyenDe_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLChuyenDe_HeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLChuyenDe_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlQLChuyenDe_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblQLChuyenDe_CapNhat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQLChuyenDe_DanhSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlQLChuyenDe_Body.setBackground(new java.awt.Color(255, 255, 255));
        pnlQLChuyenDe_Body.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        pnlQLChuyenDe_Body.setLayout(new java.awt.CardLayout());

        pnlQLChuyenDe_CapNhat.setBackground(new java.awt.Color(255, 255, 255));

        lbl2A_MaChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl2A_MaChuyenDe.setText("Mã chuyên đề");

        txt2A_MaChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl2A_TenChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl2A_TenChuyenDe.setText("Tên chuyên đề");

        txt2A_TenChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl2A_HocPhi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl2A_HocPhi.setText("Học phí");

        txt2A_HocPhi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl2A_ThoiLuong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl2A_ThoiLuong.setText("Thời lượng (số giờ)");

        txt2A_ThoiLuong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl2A_AnhDaiDien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl2A_AnhDaiDien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lbl2A_AnhDaiDien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl2A_AnhDaiDien.setMaximumSize(new java.awt.Dimension(297, 297));
        lbl2A_AnhDaiDien.setMinimumSize(new java.awt.Dimension(297, 297));
        lbl2A_AnhDaiDien.setPreferredSize(new java.awt.Dimension(297, 297));
        lbl2A_AnhDaiDien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl2A_AnhDaiDienMouseClicked(evt);
            }
        });

        lbl2A_MoTa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl2A_MoTa.setText("Mô tả chuyên đề");

        txt2A_MoTa.setColumns(20);
        txt2A_MoTa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt2A_MoTa.setRows(5);
        scr2A.setViewportView(txt2A_MoTa);

        btn2A_Truoc.setBackground(new java.awt.Color(74, 220, 255));
        btn2A_Truoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn2A_Truoc.setForeground(new java.awt.Color(255, 255, 255));
        btn2A_Truoc.setText("|<");
        btn2A_Truoc.setMaximumSize(new java.awt.Dimension(100, 40));
        btn2A_Truoc.setMinimumSize(new java.awt.Dimension(100, 40));
        btn2A_Truoc.setPreferredSize(new java.awt.Dimension(100, 40));
        btn2A_Truoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2A_TruocActionPerformed(evt);
            }
        });

        btn2A_VeTruoc.setBackground(new java.awt.Color(74, 220, 255));
        btn2A_VeTruoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn2A_VeTruoc.setForeground(new java.awt.Color(255, 255, 255));
        btn2A_VeTruoc.setText("<<");
        btn2A_VeTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2A_VeTruocActionPerformed(evt);
            }
        });

        btn2A_VeSau.setBackground(new java.awt.Color(74, 220, 255));
        btn2A_VeSau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn2A_VeSau.setForeground(new java.awt.Color(255, 255, 255));
        btn2A_VeSau.setText(">>");
        btn2A_VeSau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2A_VeSauActionPerformed(evt);
            }
        });

        btn2A_Sau.setBackground(new java.awt.Color(74, 220, 255));
        btn2A_Sau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn2A_Sau.setForeground(new java.awt.Color(255, 255, 255));
        btn2A_Sau.setText(">|");
        btn2A_Sau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2A_SauActionPerformed(evt);
            }
        });

        btn2A_Them.setBackground(new java.awt.Color(74, 220, 255));
        btn2A_Them.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn2A_Them.setForeground(new java.awt.Color(255, 255, 255));
        btn2A_Them.setText("Thêm");
        btn2A_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2A_ThemActionPerformed(evt);
            }
        });

        btn2A_Sua.setBackground(new java.awt.Color(153, 153, 153));
        btn2A_Sua.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn2A_Sua.setForeground(new java.awt.Color(51, 255, 255));
        btn2A_Sua.setText("Sửa");
        btn2A_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2A_SuaActionPerformed(evt);
            }
        });

        btn2A_Xoa.setBackground(new java.awt.Color(153, 153, 153));
        btn2A_Xoa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn2A_Xoa.setForeground(new java.awt.Color(255, 0, 0));
        btn2A_Xoa.setText("Xóa");
        btn2A_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2A_XoaActionPerformed(evt);
            }
        });

        btn2A_LamMoi.setBackground(new java.awt.Color(74, 220, 255));
        btn2A_LamMoi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn2A_LamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btn2A_LamMoi.setText("Làm mới");
        btn2A_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2A_LamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLChuyenDe_CapNhatLayout = new javax.swing.GroupLayout(pnlQLChuyenDe_CapNhat);
        pnlQLChuyenDe_CapNhat.setLayout(pnlQLChuyenDe_CapNhatLayout);
        pnlQLChuyenDe_CapNhatLayout.setHorizontalGroup(
            pnlQLChuyenDe_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLChuyenDe_CapNhatLayout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addGroup(pnlQLChuyenDe_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLChuyenDe_CapNhatLayout.createSequentialGroup()
                        .addComponent(lbl2A_MoTa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(91, 91, 91))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLChuyenDe_CapNhatLayout.createSequentialGroup()
                        .addGroup(pnlQLChuyenDe_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scr2A, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlQLChuyenDe_CapNhatLayout.createSequentialGroup()
                                .addComponent(lbl2A_AnhDaiDien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addGroup(pnlQLChuyenDe_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt2A_ThoiLuong)
                                    .addComponent(txt2A_MaChuyenDe)
                                    .addGroup(pnlQLChuyenDe_CapNhatLayout.createSequentialGroup()
                                        .addGroup(pnlQLChuyenDe_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlQLChuyenDe_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txt2A_HocPhi)
                                                .addComponent(lbl2A_HocPhi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbl2A_TenChuyenDe, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                                                .addComponent(txt2A_TenChuyenDe, javax.swing.GroupLayout.Alignment.LEADING))
                                            .addComponent(lbl2A_MaChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbl2A_ThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(pnlQLChuyenDe_CapNhatLayout.createSequentialGroup()
                                .addComponent(btn2A_Truoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn2A_VeTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn2A_VeSau, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn2A_Sau, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                                .addComponent(btn2A_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn2A_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn2A_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn2A_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(130, 130, 130))))
        );
        pnlQLChuyenDe_CapNhatLayout.setVerticalGroup(
            pnlQLChuyenDe_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLChuyenDe_CapNhatLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lbl2A_MoTa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scr2A, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlQLChuyenDe_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlQLChuyenDe_CapNhatLayout.createSequentialGroup()
                        .addComponent(lbl2A_MaChuyenDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt2A_MaChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl2A_TenChuyenDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt2A_TenChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl2A_HocPhi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt2A_HocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl2A_ThoiLuong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt2A_ThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl2A_AnhDaiDien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlQLChuyenDe_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn2A_LamMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn2A_Xoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn2A_Sua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn2A_Them, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn2A_Sau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn2A_VeSau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn2A_VeTruoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn2A_Truoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        pnlQLChuyenDe_Body.add(pnlQLChuyenDe_CapNhat, "capnhat");

        pnlQLChuyenDe_DanhSach.setBackground(new java.awt.Color(255, 255, 255));

        lbl2B_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl2B_TimKiem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl2B_TimKiem.setText("Tìm kiếm");
        lbl2B_TimKiem.setMaximumSize(new java.awt.Dimension(90, 40));
        lbl2B_TimKiem.setMinimumSize(new java.awt.Dimension(90, 40));
        lbl2B_TimKiem.setPreferredSize(new java.awt.Dimension(90, 40));

        txt2B_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt2B_TimKiem.setMaximumSize(new java.awt.Dimension(740, 40));
        txt2B_TimKiem.setMinimumSize(new java.awt.Dimension(740, 40));
        txt2B_TimKiem.setPreferredSize(new java.awt.Dimension(740, 40));
        txt2B_TimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt2B_TimKiemKeyReleased(evt);
            }
        });

        tblQLChuyenDe.setAutoCreateRowSorter(true);
        tblQLChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblQLChuyenDe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"J01", "Lập trình Java cơ bản", "5000000", "55", "...", "Không có"},
                {"...", "...", "...", "...", "...", "..."}
            },
            new String [] {
                "Mã chuyên đề", "Tên chuyên đề", "Học phí", "Thời lượng", "Hình ảnh", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQLChuyenDe.setRowHeight(40);
        tblQLChuyenDe.setSelectionBackground(new java.awt.Color(200, 248, 255));
        tblQLChuyenDe.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblQLChuyenDe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLChuyenDeMouseClicked(evt);
            }
        });
        scr2B.setViewportView(tblQLChuyenDe);
        if (tblQLChuyenDe.getColumnModel().getColumnCount() > 0) {
            tblQLChuyenDe.getColumnModel().getColumn(0).setResizable(false);
            tblQLChuyenDe.getColumnModel().getColumn(1).setResizable(false);
            tblQLChuyenDe.getColumnModel().getColumn(2).setResizable(false);
            tblQLChuyenDe.getColumnModel().getColumn(3).setResizable(false);
            tblQLChuyenDe.getColumnModel().getColumn(4).setResizable(false);
            tblQLChuyenDe.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout pnlQLChuyenDe_DanhSachLayout = new javax.swing.GroupLayout(pnlQLChuyenDe_DanhSach);
        pnlQLChuyenDe_DanhSach.setLayout(pnlQLChuyenDe_DanhSachLayout);
        pnlQLChuyenDe_DanhSachLayout.setHorizontalGroup(
            pnlQLChuyenDe_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLChuyenDe_DanhSachLayout.createSequentialGroup()
                .addGroup(pnlQLChuyenDe_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLChuyenDe_DanhSachLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(scr2B, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlQLChuyenDe_DanhSachLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(lbl2B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt2B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        pnlQLChuyenDe_DanhSachLayout.setVerticalGroup(
            pnlQLChuyenDe_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLChuyenDe_DanhSachLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlQLChuyenDe_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt2B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl2B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scr2B, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pnlQLChuyenDe_Body.add(pnlQLChuyenDe_DanhSach, "danhsach");

        javax.swing.GroupLayout pnlQLChuyenDe_2Layout = new javax.swing.GroupLayout(pnlQLChuyenDe_2);
        pnlQLChuyenDe_2.setLayout(pnlQLChuyenDe_2Layout);
        pnlQLChuyenDe_2Layout.setHorizontalGroup(
            pnlQLChuyenDe_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLChuyenDe_2Layout.createSequentialGroup()
                .addGap(0, 90, Short.MAX_VALUE)
                .addGroup(pnlQLChuyenDe_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlQLChuyenDe_Head, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlQLChuyenDe_Body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );
        pnlQLChuyenDe_2Layout.setVerticalGroup(
            pnlQLChuyenDe_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLChuyenDe_2Layout.createSequentialGroup()
                .addComponent(pnlQLChuyenDe_Head, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlQLChuyenDe_Body, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );

        javax.swing.GroupLayout pnlQLChuyenDeLayout = new javax.swing.GroupLayout(pnlQLChuyenDe);
        pnlQLChuyenDe.setLayout(pnlQLChuyenDeLayout);
        pnlQLChuyenDeLayout.setHorizontalGroup(
            pnlQLChuyenDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLChuyenDe_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlQLChuyenDeLayout.setVerticalGroup(
            pnlQLChuyenDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLChuyenDe_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlNoiDung_0.add(pnlQLChuyenDe, "qlchuyende");

        pnlQLKhoaHoc.setOpaque(false);

        pnlQLKhoaHoc_3.setOpaque(false);

        pnlQLKhoaHoc_Head.setOpaque(false);

        lblQLKhoaHoc_Title.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblQLKhoaHoc_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLKhoaHoc_Title.setText("QUẢN LÝ KHÓA HỌC");

        lblQLKhoaHoc_CapNhat.setBackground(new java.awt.Color(242, 242, 242));
        lblQLKhoaHoc_CapNhat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLKhoaHoc_CapNhat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLKhoaHoc_CapNhat.setText("Cập nhật");
        lblQLKhoaHoc_CapNhat.setOpaque(true);
        lblQLKhoaHoc_CapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLKhoaHoc_CapNhatMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLKhoaHoc_CapNhatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLKhoaHoc_CapNhatMouseExited(evt);
            }
        });

        lblQLKhoaHoc_DanhSach.setBackground(new java.awt.Color(242, 242, 242));
        lblQLKhoaHoc_DanhSach.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLKhoaHoc_DanhSach.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLKhoaHoc_DanhSach.setText("Danh sách");
        lblQLKhoaHoc_DanhSach.setOpaque(true);
        lblQLKhoaHoc_DanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLKhoaHoc_DanhSachMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLKhoaHoc_DanhSachMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLKhoaHoc_DanhSachMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlQLKhoaHoc_HeadLayout = new javax.swing.GroupLayout(pnlQLKhoaHoc_Head);
        pnlQLKhoaHoc_Head.setLayout(pnlQLKhoaHoc_HeadLayout);
        pnlQLKhoaHoc_HeadLayout.setHorizontalGroup(
            pnlQLKhoaHoc_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLKhoaHoc_HeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLKhoaHoc_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlQLKhoaHoc_HeadLayout.createSequentialGroup()
                .addComponent(lblQLKhoaHoc_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblQLKhoaHoc_DanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlQLKhoaHoc_HeadLayout.setVerticalGroup(
            pnlQLKhoaHoc_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLKhoaHoc_HeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLKhoaHoc_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlQLKhoaHoc_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblQLKhoaHoc_CapNhat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQLKhoaHoc_DanhSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlQLKhoaHoc_Body.setBackground(new java.awt.Color(255, 255, 255));
        pnlQLKhoaHoc_Body.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        pnlQLKhoaHoc_Body.setLayout(new java.awt.CardLayout());

        pnlQLKhoaHoc_CapNhat.setBackground(new java.awt.Color(255, 255, 255));

        lbl3A_MaChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl3A_MaChuyenDe.setText("Mã chuyên đề");

        txt3A_MaChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt3A_MaChuyenDe.setEnabled(false);
        txt3A_MaChuyenDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt3A_MaChuyenDeKeyReleased(evt);
            }
        });

        lbl3A_TenChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl3A_TenChuyenDe.setText("Tên chuyên đề");

        cbo3A_TenChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo3A_TenChuyenDe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lập trình Java", "Lập trình Python" }));
        cbo3A_TenChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo3A_TenChuyenDeActionPerformed(evt);
            }
        });

        lbl3A_HocPhi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl3A_HocPhi.setText("Học phí");

        txt3A_HocPhi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl3A_ThoiLuong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl3A_ThoiLuong.setText("Thời lượng (số giờ)");

        txt3A_ThoiLuong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl3A_MaKhoaHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl3A_MaKhoaHoc.setText("Mã khóa học");

        txt3A_MaKhoaHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl3A_NgayKhaiGiang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl3A_NgayKhaiGiang.setText("Ngày khai giảng (20-10-2021)");

        txt3A_NgayKhaiGiang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl3A_NgayTao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl3A_NgayTao.setText("Ngày tạo (20-10-2021)");

        txt3A_NgayTao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt3A_NgayTao.setEnabled(false);

        lbl3A_GhiChu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl3A_GhiChu.setText("Ghi chú");

        txt3A_GhiChu.setColumns(20);
        txt3A_GhiChu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt3A_GhiChu.setRows(5);
        scr3A.setViewportView(txt3A_GhiChu);

        btn3A_Truoc.setBackground(new java.awt.Color(74, 220, 255));
        btn3A_Truoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn3A_Truoc.setForeground(new java.awt.Color(255, 255, 255));
        btn3A_Truoc.setText("|<");
        btn3A_Truoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3A_TruocActionPerformed(evt);
            }
        });

        btn3A_VeTruoc.setBackground(new java.awt.Color(74, 220, 255));
        btn3A_VeTruoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn3A_VeTruoc.setForeground(new java.awt.Color(255, 255, 255));
        btn3A_VeTruoc.setText("<<");
        btn3A_VeTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3A_VeTruocActionPerformed(evt);
            }
        });

        btn3A_VeSau.setBackground(new java.awt.Color(74, 220, 255));
        btn3A_VeSau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn3A_VeSau.setForeground(new java.awt.Color(255, 255, 255));
        btn3A_VeSau.setText(">>");
        btn3A_VeSau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3A_VeSauActionPerformed(evt);
            }
        });

        btn3A_Sau.setBackground(new java.awt.Color(74, 220, 255));
        btn3A_Sau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn3A_Sau.setForeground(new java.awt.Color(255, 255, 255));
        btn3A_Sau.setText(">|");
        btn3A_Sau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3A_SauActionPerformed(evt);
            }
        });

        btn3A_Them.setBackground(new java.awt.Color(74, 220, 255));
        btn3A_Them.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn3A_Them.setForeground(new java.awt.Color(255, 255, 255));
        btn3A_Them.setText("Thêm");
        btn3A_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3A_ThemActionPerformed(evt);
            }
        });

        btn3A_Sua.setBackground(new java.awt.Color(153, 153, 153));
        btn3A_Sua.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn3A_Sua.setForeground(new java.awt.Color(51, 255, 255));
        btn3A_Sua.setText("Sửa");
        btn3A_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3A_SuaActionPerformed(evt);
            }
        });

        btn3A_Xoa.setBackground(new java.awt.Color(153, 153, 153));
        btn3A_Xoa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn3A_Xoa.setForeground(new java.awt.Color(255, 0, 0));
        btn3A_Xoa.setText("Xóa");
        btn3A_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3A_XoaActionPerformed(evt);
            }
        });

        btn3A_NhapMoi.setBackground(new java.awt.Color(74, 220, 255));
        btn3A_NhapMoi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn3A_NhapMoi.setForeground(new java.awt.Color(255, 255, 255));
        btn3A_NhapMoi.setText("Làm mới");
        btn3A_NhapMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3A_NhapMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLKhoaHoc_CapNhatLayout = new javax.swing.GroupLayout(pnlQLKhoaHoc_CapNhat);
        pnlQLKhoaHoc_CapNhat.setLayout(pnlQLKhoaHoc_CapNhatLayout);
        pnlQLKhoaHoc_CapNhatLayout.setHorizontalGroup(
            pnlQLKhoaHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLKhoaHoc_CapNhatLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(pnlQLKhoaHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl3A_GhiChu)
                    .addComponent(scr3A, javax.swing.GroupLayout.PREFERRED_SIZE, 986, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlQLKhoaHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pnlQLKhoaHoc_CapNhatLayout.createSequentialGroup()
                            .addGroup(pnlQLKhoaHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt3A_MaChuyenDe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl3A_MaChuyenDe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl3A_TenChuyenDe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt3A_HocPhi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl3A_HocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt3A_ThoiLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl3A_ThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbo3A_TenChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(48, 48, 48)
                            .addGroup(pnlQLKhoaHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt3A_NgayKhaiGiang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl3A_NgayTao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl3A_NgayKhaiGiang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt3A_MaKhoaHoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl3A_MaKhoaHoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt3A_NgayTao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLKhoaHoc_CapNhatLayout.createSequentialGroup()
                            .addComponent(btn3A_Truoc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn3A_VeTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn3A_VeSau, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn3A_Sau, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn3A_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn3A_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn3A_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn3A_NhapMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(87, 87, 87))
        );
        pnlQLKhoaHoc_CapNhatLayout.setVerticalGroup(
            pnlQLKhoaHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLKhoaHoc_CapNhatLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlQLKhoaHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLKhoaHoc_CapNhatLayout.createSequentialGroup()
                        .addComponent(lbl3A_MaKhoaHoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt3A_MaKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl3A_NgayKhaiGiang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt3A_NgayKhaiGiang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl3A_NgayTao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt3A_NgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlQLKhoaHoc_CapNhatLayout.createSequentialGroup()
                        .addComponent(lbl3A_MaChuyenDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt3A_MaChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl3A_TenChuyenDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbo3A_TenChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl3A_HocPhi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt3A_HocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl3A_ThoiLuong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt3A_ThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl3A_GhiChu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scr3A, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(pnlQLKhoaHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn3A_NhapMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3A_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3A_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3A_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3A_Sau, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3A_VeSau, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3A_VeTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3A_Truoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        pnlQLKhoaHoc_Body.add(pnlQLKhoaHoc_CapNhat, "capnhat");

        pnlQLKhoaHoc_DanhSach.setBackground(new java.awt.Color(255, 255, 255));

        lbl3B_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl3B_TimKiem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl3B_TimKiem.setText("Tìm kiếm");
        lbl3B_TimKiem.setMaximumSize(new java.awt.Dimension(90, 40));
        lbl3B_TimKiem.setMinimumSize(new java.awt.Dimension(90, 40));
        lbl3B_TimKiem.setPreferredSize(new java.awt.Dimension(90, 40));

        txt3B_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt3B_TimKiem.setMaximumSize(new java.awt.Dimension(740, 40));
        txt3B_TimKiem.setMinimumSize(new java.awt.Dimension(740, 40));
        txt3B_TimKiem.setPreferredSize(new java.awt.Dimension(740, 40));
        txt3B_TimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt3B_TimKiemKeyReleased(evt);
            }
        });

        tblQLKhoaHoc.setAutoCreateRowSorter(true);
        tblQLKhoaHoc.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblQLKhoaHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"KH01", "J01", "55", "5000000", "06/06/2021", "NV01", "20/05/2021", "Không có"},
                {"...", "...", "...", "...", "...", "...", "...", "..."}
            },
            new String [] {
                "Mã khóa học", "Mã chuyên đề", "Học phí", "Thời lượng", "Ngày khai giảng", "Người tạo", "Ngày tạo", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQLKhoaHoc.setRowHeight(40);
        tblQLKhoaHoc.setSelectionBackground(new java.awt.Color(200, 248, 255));
        tblQLKhoaHoc.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblQLKhoaHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLKhoaHocMouseClicked(evt);
            }
        });
        scr3B.setViewportView(tblQLKhoaHoc);
        if (tblQLKhoaHoc.getColumnModel().getColumnCount() > 0) {
            tblQLKhoaHoc.getColumnModel().getColumn(0).setResizable(false);
            tblQLKhoaHoc.getColumnModel().getColumn(1).setResizable(false);
            tblQLKhoaHoc.getColumnModel().getColumn(2).setResizable(false);
            tblQLKhoaHoc.getColumnModel().getColumn(3).setResizable(false);
            tblQLKhoaHoc.getColumnModel().getColumn(4).setResizable(false);
            tblQLKhoaHoc.getColumnModel().getColumn(5).setResizable(false);
            tblQLKhoaHoc.getColumnModel().getColumn(6).setResizable(false);
            tblQLKhoaHoc.getColumnModel().getColumn(7).setResizable(false);
        }

        javax.swing.GroupLayout pnlQLKhoaHoc_DanhSachLayout = new javax.swing.GroupLayout(pnlQLKhoaHoc_DanhSach);
        pnlQLKhoaHoc_DanhSach.setLayout(pnlQLKhoaHoc_DanhSachLayout);
        pnlQLKhoaHoc_DanhSachLayout.setHorizontalGroup(
            pnlQLKhoaHoc_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLKhoaHoc_DanhSachLayout.createSequentialGroup()
                .addGroup(pnlQLKhoaHoc_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLKhoaHoc_DanhSachLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(scr3B, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlQLKhoaHoc_DanhSachLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(lbl3B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt3B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        pnlQLKhoaHoc_DanhSachLayout.setVerticalGroup(
            pnlQLKhoaHoc_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLKhoaHoc_DanhSachLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlQLKhoaHoc_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt3B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl3B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scr3B, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pnlQLKhoaHoc_Body.add(pnlQLKhoaHoc_DanhSach, "danhsach");

        javax.swing.GroupLayout pnlQLKhoaHoc_3Layout = new javax.swing.GroupLayout(pnlQLKhoaHoc_3);
        pnlQLKhoaHoc_3.setLayout(pnlQLKhoaHoc_3Layout);
        pnlQLKhoaHoc_3Layout.setHorizontalGroup(
            pnlQLKhoaHoc_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLKhoaHoc_3Layout.createSequentialGroup()
                .addGap(0, 90, Short.MAX_VALUE)
                .addGroup(pnlQLKhoaHoc_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlQLKhoaHoc_Head, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlQLKhoaHoc_Body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );
        pnlQLKhoaHoc_3Layout.setVerticalGroup(
            pnlQLKhoaHoc_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLKhoaHoc_3Layout.createSequentialGroup()
                .addComponent(pnlQLKhoaHoc_Head, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlQLKhoaHoc_Body, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );

        javax.swing.GroupLayout pnlQLKhoaHocLayout = new javax.swing.GroupLayout(pnlQLKhoaHoc);
        pnlQLKhoaHoc.setLayout(pnlQLKhoaHocLayout);
        pnlQLKhoaHocLayout.setHorizontalGroup(
            pnlQLKhoaHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLKhoaHoc_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlQLKhoaHocLayout.setVerticalGroup(
            pnlQLKhoaHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLKhoaHoc_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlNoiDung_0.add(pnlQLKhoaHoc, "qlkhoahoc");

        pnlQLNguoiHoc.setOpaque(false);

        pnlQLNguoiHoc_4.setOpaque(false);

        pnlQLNguoiHoc_Head.setOpaque(false);

        lblQLNguoiHoc_Title.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblQLNguoiHoc_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLNguoiHoc_Title.setText("QUẢN LÝ NGƯỜI HỌC");

        lblQLNguoiHoc_CapNhat.setBackground(new java.awt.Color(242, 242, 242));
        lblQLNguoiHoc_CapNhat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLNguoiHoc_CapNhat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLNguoiHoc_CapNhat.setText("Cập nhật");
        lblQLNguoiHoc_CapNhat.setOpaque(true);
        lblQLNguoiHoc_CapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLNguoiHoc_CapNhatMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLNguoiHoc_CapNhatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLNguoiHoc_CapNhatMouseExited(evt);
            }
        });

        lblQLNguoiHoc_DanhSach.setBackground(new java.awt.Color(242, 242, 242));
        lblQLNguoiHoc_DanhSach.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLNguoiHoc_DanhSach.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLNguoiHoc_DanhSach.setText("Danh sách");
        lblQLNguoiHoc_DanhSach.setOpaque(true);
        lblQLNguoiHoc_DanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLNguoiHoc_DanhSachMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLNguoiHoc_DanhSachMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLNguoiHoc_DanhSachMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlQLNguoiHoc_HeadLayout = new javax.swing.GroupLayout(pnlQLNguoiHoc_Head);
        pnlQLNguoiHoc_Head.setLayout(pnlQLNguoiHoc_HeadLayout);
        pnlQLNguoiHoc_HeadLayout.setHorizontalGroup(
            pnlQLNguoiHoc_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLNguoiHoc_HeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLNguoiHoc_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlQLNguoiHoc_HeadLayout.createSequentialGroup()
                .addComponent(lblQLNguoiHoc_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblQLNguoiHoc_DanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 711, Short.MAX_VALUE))
        );
        pnlQLNguoiHoc_HeadLayout.setVerticalGroup(
            pnlQLNguoiHoc_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLNguoiHoc_HeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLNguoiHoc_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlQLNguoiHoc_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblQLNguoiHoc_CapNhat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQLNguoiHoc_DanhSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlQLNguoiHoc_Body.setBackground(new java.awt.Color(255, 255, 255));
        pnlQLNguoiHoc_Body.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        pnlQLNguoiHoc_Body.setLayout(new java.awt.CardLayout());

        pnlQLNguoiHoc_CapNhat.setBackground(new java.awt.Color(255, 255, 255));

        lbl4A_MaNguoiHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl4A_MaNguoiHoc.setText("Mã người học");

        txt4A_MaNguoiHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl4A_HoTen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl4A_HoTen.setText("Họ tên");

        txt4A_HoTen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl4A_GioiTinh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl4A_GioiTinh.setText("Giới tính");

        cbo4A_GioiTinh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo4A_GioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        lbl4A_NgaySinh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl4A_NgaySinh.setText("Ngày sinh (20-10-2021)");

        txt4A_NgaySinh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl4A_SoDienThoai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl4A_SoDienThoai.setText("Số điện thoại");

        txt4A_SoDienThoai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl4A_Email.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl4A_Email.setText("Email");

        txt4A_Email.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl4A_DiaChi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl4A_DiaChi.setText("Địa chỉ");

        txt4A_DiaChi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl4A_Anh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl4A_Anh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl4A_Anh.setText("Ảnh");
        lbl4A_Anh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lbl4A_Anh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl4A_Anh.setMaximumSize(new java.awt.Dimension(174, 174));
        lbl4A_Anh.setMinimumSize(new java.awt.Dimension(174, 174));
        lbl4A_Anh.setPreferredSize(new java.awt.Dimension(174, 174));
        lbl4A_Anh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl4A_AnhMouseClicked(evt);
            }
        });

        lbl4A_NgayDangKy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl4A_NgayDangKy.setText("Ngày đăng ký (20-10-2021)");

        txt4A_NgayDangKy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt4A_NgayDangKy.setEnabled(false);

        lbl4A_GhiChu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl4A_GhiChu.setText("Ghi chú");

        txt4A_GhiChu.setColumns(20);
        txt4A_GhiChu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt4A_GhiChu.setRows(5);
        scr4A.setViewportView(txt4A_GhiChu);

        btn4A_Truoc.setBackground(new java.awt.Color(74, 220, 255));
        btn4A_Truoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn4A_Truoc.setForeground(new java.awt.Color(255, 255, 255));
        btn4A_Truoc.setText("|<");
        btn4A_Truoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4A_TruocActionPerformed(evt);
            }
        });

        btn4A_VeTruoc.setBackground(new java.awt.Color(74, 220, 255));
        btn4A_VeTruoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn4A_VeTruoc.setForeground(new java.awt.Color(255, 255, 255));
        btn4A_VeTruoc.setText("<<");
        btn4A_VeTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4A_VeTruocActionPerformed(evt);
            }
        });

        btn4A_VeSau.setBackground(new java.awt.Color(74, 220, 255));
        btn4A_VeSau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn4A_VeSau.setForeground(new java.awt.Color(255, 255, 255));
        btn4A_VeSau.setText(">>");
        btn4A_VeSau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4A_VeSauActionPerformed(evt);
            }
        });

        btn4A_Sau.setBackground(new java.awt.Color(74, 220, 255));
        btn4A_Sau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn4A_Sau.setForeground(new java.awt.Color(255, 255, 255));
        btn4A_Sau.setText(">|");
        btn4A_Sau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4A_SauActionPerformed(evt);
            }
        });

        btn4A_Them.setBackground(new java.awt.Color(74, 220, 255));
        btn4A_Them.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn4A_Them.setForeground(new java.awt.Color(255, 255, 255));
        btn4A_Them.setText("Thêm");
        btn4A_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4A_ThemActionPerformed(evt);
            }
        });

        btn4A_Sua.setBackground(new java.awt.Color(153, 153, 153));
        btn4A_Sua.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn4A_Sua.setForeground(new java.awt.Color(51, 255, 255));
        btn4A_Sua.setText("Sửa");
        btn4A_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4A_SuaActionPerformed(evt);
            }
        });

        btn4A_Xoa.setBackground(new java.awt.Color(153, 153, 153));
        btn4A_Xoa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn4A_Xoa.setForeground(new java.awt.Color(255, 0, 0));
        btn4A_Xoa.setText("Xóa");
        btn4A_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4A_XoaActionPerformed(evt);
            }
        });

        btn4A_LamMoi.setBackground(new java.awt.Color(74, 220, 255));
        btn4A_LamMoi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn4A_LamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btn4A_LamMoi.setText("Làm mới");
        btn4A_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4A_LamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLNguoiHoc_CapNhatLayout = new javax.swing.GroupLayout(pnlQLNguoiHoc_CapNhat);
        pnlQLNguoiHoc_CapNhat.setLayout(pnlQLNguoiHoc_CapNhatLayout);
        pnlQLNguoiHoc_CapNhatLayout.setHorizontalGroup(
            pnlQLNguoiHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLNguoiHoc_CapNhatLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(pnlQLNguoiHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scr4A, javax.swing.GroupLayout.PREFERRED_SIZE, 986, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlQLNguoiHoc_CapNhatLayout.createSequentialGroup()
                        .addGroup(pnlQLNguoiHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlQLNguoiHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt4A_SoDienThoai, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbl4A_SoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlQLNguoiHoc_CapNhatLayout.createSequentialGroup()
                                .addComponent(btn4A_Truoc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn4A_VeTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn4A_VeSau, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn4A_Sau, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlQLNguoiHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt4A_MaNguoiHoc, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbl4A_MaNguoiHoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl4A_HoTen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl4A_GioiTinh, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                                .addComponent(txt4A_NgaySinh, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbl4A_NgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                                .addComponent(cbo4A_GioiTinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt4A_HoTen))
                            .addComponent(lbl4A_GhiChu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlQLNguoiHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLNguoiHoc_CapNhatLayout.createSequentialGroup()
                                .addComponent(btn4A_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn4A_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn4A_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn4A_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLNguoiHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt4A_DiaChi, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt4A_NgayDangKy, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbl4A_NgayDangKy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                                .addComponent(lbl4A_DiaChi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt4A_Email, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbl4A_Email, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLNguoiHoc_CapNhatLayout.createSequentialGroup()
                                    .addComponent(lbl4A_Anh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(148, 148, 148))))))
                .addGap(91, 91, 91))
        );
        pnlQLNguoiHoc_CapNhatLayout.setVerticalGroup(
            pnlQLNguoiHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLNguoiHoc_CapNhatLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlQLNguoiHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLNguoiHoc_CapNhatLayout.createSequentialGroup()
                        .addComponent(lbl4A_Email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt4A_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl4A_DiaChi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt4A_DiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlQLNguoiHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlQLNguoiHoc_CapNhatLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(cbo4A_GioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlQLNguoiHoc_CapNhatLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl4A_NgayDangKy)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt4A_NgayDangKy, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlQLNguoiHoc_CapNhatLayout.createSequentialGroup()
                        .addComponent(lbl4A_MaNguoiHoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt4A_MaNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl4A_HoTen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt4A_HoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl4A_GioiTinh)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlQLNguoiHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLNguoiHoc_CapNhatLayout.createSequentialGroup()
                        .addComponent(lbl4A_NgaySinh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt4A_NgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl4A_SoDienThoai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt4A_SoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl4A_GhiChu))
                    .addComponent(lbl4A_Anh, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scr4A, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlQLNguoiHoc_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn4A_LamMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn4A_Xoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn4A_Sua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn4A_Them, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn4A_Sau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn4A_VeSau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn4A_VeTruoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn4A_Truoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        pnlQLNguoiHoc_Body.add(pnlQLNguoiHoc_CapNhat, "capnhat");

        pnlQLNguoiHoc_DanhSach.setBackground(new java.awt.Color(255, 255, 255));

        lbl4B_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl4B_TimKiem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl4B_TimKiem.setText("Tìm kiếm");
        lbl4B_TimKiem.setMaximumSize(new java.awt.Dimension(90, 40));
        lbl4B_TimKiem.setMinimumSize(new java.awt.Dimension(90, 40));
        lbl4B_TimKiem.setPreferredSize(new java.awt.Dimension(90, 40));

        txt4B_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt4B_TimKiem.setMaximumSize(new java.awt.Dimension(740, 40));
        txt4B_TimKiem.setMinimumSize(new java.awt.Dimension(740, 40));
        txt4B_TimKiem.setPreferredSize(new java.awt.Dimension(740, 40));
        txt4B_TimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt4B_TimKiemKeyReleased(evt);
            }
        });

        tblQLNguoiHoc.setAutoCreateRowSorter(true);
        tblQLNguoiHoc.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblQLNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PC01234", "Nguyễn Văn Chính", "Nam", "12/12/2004", "0987987987", "chinhnvpc01234@fpt.edu.vn", "Cần Thơ", "...", "...", "NV01", "01/06/2021"},
                {"...", "...", "...", "...", "...", "...", "...", "...", "...", "...", "..."}
            },
            new String [] {
                "Mã người học", "Họ tên", "Giới tính", "Ngày sinh", "SĐT", "Email", "Địa chỉ", "Ảnh", "Ghi chú", "Người tạo", "Ngày đăng ký"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQLNguoiHoc.setRowHeight(40);
        tblQLNguoiHoc.setSelectionBackground(new java.awt.Color(200, 248, 255));
        tblQLNguoiHoc.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblQLNguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLNguoiHocMouseClicked(evt);
            }
        });
        scr4B.setViewportView(tblQLNguoiHoc);
        if (tblQLNguoiHoc.getColumnModel().getColumnCount() > 0) {
            tblQLNguoiHoc.getColumnModel().getColumn(0).setResizable(false);
            tblQLNguoiHoc.getColumnModel().getColumn(1).setResizable(false);
            tblQLNguoiHoc.getColumnModel().getColumn(2).setResizable(false);
            tblQLNguoiHoc.getColumnModel().getColumn(3).setResizable(false);
            tblQLNguoiHoc.getColumnModel().getColumn(4).setResizable(false);
            tblQLNguoiHoc.getColumnModel().getColumn(5).setResizable(false);
            tblQLNguoiHoc.getColumnModel().getColumn(6).setResizable(false);
            tblQLNguoiHoc.getColumnModel().getColumn(7).setResizable(false);
            tblQLNguoiHoc.getColumnModel().getColumn(8).setResizable(false);
            tblQLNguoiHoc.getColumnModel().getColumn(9).setResizable(false);
            tblQLNguoiHoc.getColumnModel().getColumn(10).setResizable(false);
        }

        javax.swing.GroupLayout pnlQLNguoiHoc_DanhSachLayout = new javax.swing.GroupLayout(pnlQLNguoiHoc_DanhSach);
        pnlQLNguoiHoc_DanhSach.setLayout(pnlQLNguoiHoc_DanhSachLayout);
        pnlQLNguoiHoc_DanhSachLayout.setHorizontalGroup(
            pnlQLNguoiHoc_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLNguoiHoc_DanhSachLayout.createSequentialGroup()
                .addGroup(pnlQLNguoiHoc_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLNguoiHoc_DanhSachLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(scr4B, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlQLNguoiHoc_DanhSachLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(lbl4B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt4B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlQLNguoiHoc_DanhSachLayout.setVerticalGroup(
            pnlQLNguoiHoc_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLNguoiHoc_DanhSachLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlQLNguoiHoc_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt4B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl4B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scr4B, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pnlQLNguoiHoc_Body.add(pnlQLNguoiHoc_DanhSach, "danhsach");

        javax.swing.GroupLayout pnlQLNguoiHoc_4Layout = new javax.swing.GroupLayout(pnlQLNguoiHoc_4);
        pnlQLNguoiHoc_4.setLayout(pnlQLNguoiHoc_4Layout);
        pnlQLNguoiHoc_4Layout.setHorizontalGroup(
            pnlQLNguoiHoc_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLNguoiHoc_4Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(pnlQLNguoiHoc_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlQLNguoiHoc_Head, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlQLNguoiHoc_Body, javax.swing.GroupLayout.PREFERRED_SIZE, 1168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        pnlQLNguoiHoc_4Layout.setVerticalGroup(
            pnlQLNguoiHoc_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLNguoiHoc_4Layout.createSequentialGroup()
                .addComponent(pnlQLNguoiHoc_Head, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlQLNguoiHoc_Body, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout pnlQLNguoiHocLayout = new javax.swing.GroupLayout(pnlQLNguoiHoc);
        pnlQLNguoiHoc.setLayout(pnlQLNguoiHocLayout);
        pnlQLNguoiHocLayout.setHorizontalGroup(
            pnlQLNguoiHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLNguoiHoc_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlQLNguoiHocLayout.setVerticalGroup(
            pnlQLNguoiHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLNguoiHoc_4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlNoiDung_0.add(pnlQLNguoiHoc, "qlnguoihoc");

        pnlQLHocVien.setOpaque(false);

        pnlQLHocVien_5.setOpaque(false);

        pnlQLHocVien_Head.setOpaque(false);

        lblQLHocVien_Title.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblQLHocVien_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLHocVien_Title.setText("QUẢN LÝ HỌC VIÊN");

        lblQLHocVien_HocVien.setBackground(new java.awt.Color(242, 242, 242));
        lblQLHocVien_HocVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLHocVien_HocVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLHocVien_HocVien.setText("Học viên");
        lblQLHocVien_HocVien.setOpaque(true);
        lblQLHocVien_HocVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLHocVien_HocVienMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLHocVien_HocVienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLHocVien_HocVienMouseExited(evt);
            }
        });

        lblQLHocVien_NguoiHoc.setBackground(new java.awt.Color(242, 242, 242));
        lblQLHocVien_NguoiHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLHocVien_NguoiHoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLHocVien_NguoiHoc.setText("Người học");
        lblQLHocVien_NguoiHoc.setOpaque(true);
        lblQLHocVien_NguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLHocVien_NguoiHocMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLHocVien_NguoiHocMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLHocVien_NguoiHocMouseExited(evt);
            }
        });

        lblQLHocVien_ChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLHocVien_ChuyenDe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLHocVien_ChuyenDe.setText("CHUYÊN ĐỀ");

        cboQLHocVien_ChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboQLHocVien_ChuyenDe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lập trình Java cơ bản" }));
        cboQLHocVien_ChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboQLHocVien_ChuyenDeActionPerformed(evt);
            }
        });

        lblQLHocVien_KhoaHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLHocVien_KhoaHoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLHocVien_KhoaHoc.setText("KHÓA HỌC");

        cboQLHocVien_KhoaHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboQLHocVien_KhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "KH01" }));
        cboQLHocVien_KhoaHoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboQLHocVien_KhoaHocItemStateChanged(evt);
            }
        });
        cboQLHocVien_KhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboQLHocVien_KhoaHocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLHocVien_HeadLayout = new javax.swing.GroupLayout(pnlQLHocVien_Head);
        pnlQLHocVien_Head.setLayout(pnlQLHocVien_HeadLayout);
        pnlQLHocVien_HeadLayout.setHorizontalGroup(
            pnlQLHocVien_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLHocVien_HeadLayout.createSequentialGroup()
                .addGroup(pnlQLHocVien_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLHocVien_HeadLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblQLHocVien_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlQLHocVien_HeadLayout.createSequentialGroup()
                        .addComponent(lblQLHocVien_HocVien, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblQLHocVien_NguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblQLHocVien_ChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboQLHocVien_ChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblQLHocVien_KhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboQLHocVien_KhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlQLHocVien_HeadLayout.setVerticalGroup(
            pnlQLHocVien_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLHocVien_HeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLHocVien_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlQLHocVien_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblQLHocVien_HocVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlQLHocVien_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboQLHocVien_ChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblQLHocVien_ChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlQLHocVien_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblQLHocVien_NguoiHoc, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                        .addComponent(cboQLHocVien_KhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblQLHocVien_KhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pnlQLHocVien_Body.setBackground(new java.awt.Color(255, 255, 255));
        pnlQLHocVien_Body.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        pnlQLHocVien_Body.setLayout(new java.awt.CardLayout());

        pnlQLHocVien_HocVien.setBackground(new java.awt.Color(255, 255, 255));

        lbl5A_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl5A_TimKiem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl5A_TimKiem.setText("Tìm kiếm");
        lbl5A_TimKiem.setMaximumSize(new java.awt.Dimension(90, 40));
        lbl5A_TimKiem.setMinimumSize(new java.awt.Dimension(90, 40));
        lbl5A_TimKiem.setPreferredSize(new java.awt.Dimension(90, 40));

        txt5A_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt5A_TimKiem.setMaximumSize(new java.awt.Dimension(740, 40));
        txt5A_TimKiem.setMinimumSize(new java.awt.Dimension(740, 40));
        txt5A_TimKiem.setPreferredSize(new java.awt.Dimension(740, 40));
        txt5A_TimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt5A_TimKiemKeyReleased(evt);
            }
        });

        tblQLHocVien_HocVien.setAutoCreateRowSorter(true);
        tblQLHocVien_HocVien.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblQLHocVien_HocVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"KH01", "PC01234", "Nguyễn Văn Chính", "9.9"},
                {"...", "...", "...", "..."}
            },
            new String [] {
                "Mã khóa học", "Mã người học", "Họ tên", "Điểm trung bình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQLHocVien_HocVien.setRowHeight(40);
        tblQLHocVien_HocVien.setSelectionBackground(new java.awt.Color(200, 248, 255));
        tblQLHocVien_HocVien.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblQLHocVien_HocVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLHocVien_HocVienMouseClicked(evt);
            }
        });
        scr5A.setViewportView(tblQLHocVien_HocVien);
        if (tblQLHocVien_HocVien.getColumnModel().getColumnCount() > 0) {
            tblQLHocVien_HocVien.getColumnModel().getColumn(0).setResizable(false);
            tblQLHocVien_HocVien.getColumnModel().getColumn(1).setResizable(false);
            tblQLHocVien_HocVien.getColumnModel().getColumn(2).setResizable(false);
            tblQLHocVien_HocVien.getColumnModel().getColumn(3).setResizable(false);
        }

        btn5A_CapNhat.setBackground(new java.awt.Color(74, 220, 255));
        btn5A_CapNhat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn5A_CapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btn5A_CapNhat.setText("Cập nhật điểm");
        btn5A_CapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5A_CapNhatActionPerformed(evt);
            }
        });

        btn5A_Xoa.setBackground(new java.awt.Color(153, 153, 153));
        btn5A_Xoa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn5A_Xoa.setForeground(new java.awt.Color(255, 0, 0));
        btn5A_Xoa.setText("Xóa khỏi khóa học");
        btn5A_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5A_XoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLHocVien_HocVienLayout = new javax.swing.GroupLayout(pnlQLHocVien_HocVien);
        pnlQLHocVien_HocVien.setLayout(pnlQLHocVien_HocVienLayout);
        pnlQLHocVien_HocVienLayout.setHorizontalGroup(
            pnlQLHocVien_HocVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLHocVien_HocVienLayout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(lbl5A_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt5A_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLHocVien_HocVienLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlQLHocVien_HocVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scr5A, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlQLHocVien_HocVienLayout.createSequentialGroup()
                        .addComponent(btn5A_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn5A_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        pnlQLHocVien_HocVienLayout.setVerticalGroup(
            pnlQLHocVien_HocVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLHocVien_HocVienLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlQLHocVien_HocVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt5A_TimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl5A_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scr5A, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(pnlQLHocVien_HocVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn5A_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn5A_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        pnlQLHocVien_Body.add(pnlQLHocVien_HocVien, "hocvien");

        pnlQLHocVien_NguoiHoc.setBackground(new java.awt.Color(255, 255, 255));

        lbl5B_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl5B_TimKiem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl5B_TimKiem.setText("Tìm kiếm");
        lbl5B_TimKiem.setMaximumSize(new java.awt.Dimension(90, 40));
        lbl5B_TimKiem.setMinimumSize(new java.awt.Dimension(90, 40));
        lbl5B_TimKiem.setPreferredSize(new java.awt.Dimension(90, 40));

        txt5B_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt5B_TimKiem.setMaximumSize(new java.awt.Dimension(740, 40));
        txt5B_TimKiem.setMinimumSize(new java.awt.Dimension(740, 40));
        txt5B_TimKiem.setPreferredSize(new java.awt.Dimension(740, 40));
        txt5B_TimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt5B_TimKiemKeyReleased(evt);
            }
        });

        tblQLHocVien_NguoiHoc.setAutoCreateRowSorter(true);
        tblQLHocVien_NguoiHoc.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblQLHocVien_NguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"...", "...", "...", "...", "...", "...", "..."}
            },
            new String [] {
                "Mã người học", "Họ tên", "Giới tính", "Ngày sinh", "SĐT", "Email", "Địa chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQLHocVien_NguoiHoc.setRowHeight(40);
        tblQLHocVien_NguoiHoc.setSelectionBackground(new java.awt.Color(200, 248, 255));
        tblQLHocVien_NguoiHoc.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblQLHocVien_NguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLHocVien_NguoiHocMouseClicked(evt);
            }
        });
        scr5B.setViewportView(tblQLHocVien_NguoiHoc);
        if (tblQLHocVien_NguoiHoc.getColumnModel().getColumnCount() > 0) {
            tblQLHocVien_NguoiHoc.getColumnModel().getColumn(0).setResizable(false);
            tblQLHocVien_NguoiHoc.getColumnModel().getColumn(1).setResizable(false);
            tblQLHocVien_NguoiHoc.getColumnModel().getColumn(2).setResizable(false);
            tblQLHocVien_NguoiHoc.getColumnModel().getColumn(3).setResizable(false);
            tblQLHocVien_NguoiHoc.getColumnModel().getColumn(4).setResizable(false);
            tblQLHocVien_NguoiHoc.getColumnModel().getColumn(5).setResizable(false);
            tblQLHocVien_NguoiHoc.getColumnModel().getColumn(6).setResizable(false);
        }

        btn5B_Them.setBackground(new java.awt.Color(74, 220, 255));
        btn5B_Them.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn5B_Them.setForeground(new java.awt.Color(255, 255, 255));
        btn5B_Them.setText("Thêm vào khóa học");
        btn5B_Them.setPreferredSize(new java.awt.Dimension(200, 40));
        btn5B_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5B_ThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLHocVien_NguoiHocLayout = new javax.swing.GroupLayout(pnlQLHocVien_NguoiHoc);
        pnlQLHocVien_NguoiHoc.setLayout(pnlQLHocVien_NguoiHocLayout);
        pnlQLHocVien_NguoiHocLayout.setHorizontalGroup(
            pnlQLHocVien_NguoiHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLHocVien_NguoiHocLayout.createSequentialGroup()
                .addGroup(pnlQLHocVien_NguoiHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLHocVien_NguoiHocLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(pnlQLHocVien_NguoiHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn5B_Them, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scr5B, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlQLHocVien_NguoiHocLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(lbl5B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt5B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        pnlQLHocVien_NguoiHocLayout.setVerticalGroup(
            pnlQLHocVien_NguoiHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLHocVien_NguoiHocLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlQLHocVien_NguoiHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt5B_TimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl5B_TimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scr5B, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(btn5B_Them, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pnlQLHocVien_Body.add(pnlQLHocVien_NguoiHoc, "nguoihoc");

        javax.swing.GroupLayout pnlQLHocVien_5Layout = new javax.swing.GroupLayout(pnlQLHocVien_5);
        pnlQLHocVien_5.setLayout(pnlQLHocVien_5Layout);
        pnlQLHocVien_5Layout.setHorizontalGroup(
            pnlQLHocVien_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLHocVien_5Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(pnlQLHocVien_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlQLHocVien_Head, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlQLHocVien_Body, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        pnlQLHocVien_5Layout.setVerticalGroup(
            pnlQLHocVien_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLHocVien_5Layout.createSequentialGroup()
                .addComponent(pnlQLHocVien_Head, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlQLHocVien_Body, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );

        javax.swing.GroupLayout pnlQLHocVienLayout = new javax.swing.GroupLayout(pnlQLHocVien);
        pnlQLHocVien.setLayout(pnlQLHocVienLayout);
        pnlQLHocVienLayout.setHorizontalGroup(
            pnlQLHocVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLHocVien_5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlQLHocVienLayout.setVerticalGroup(
            pnlQLHocVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLHocVien_5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlNoiDung_0.add(pnlQLHocVien, "qlhocvien");

        pnlQLNhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlQLNhanVien.setOpaque(false);

        pnlQLNhanVien_6.setOpaque(false);

        pnlQLNhanVien_Head.setOpaque(false);

        lblQLNhanVien_Title.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblQLNhanVien_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLNhanVien_Title.setText("QUẢN LÝ NHÂN VIÊN");

        lblQLNhanVien_CapNhat.setBackground(new java.awt.Color(242, 242, 242));
        lblQLNhanVien_CapNhat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLNhanVien_CapNhat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLNhanVien_CapNhat.setText("Cập nhật");
        lblQLNhanVien_CapNhat.setOpaque(true);
        lblQLNhanVien_CapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLNhanVien_CapNhatMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLNhanVien_CapNhatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLNhanVien_CapNhatMouseExited(evt);
            }
        });

        lblQLNhanVien_DanhSach.setBackground(new java.awt.Color(242, 242, 242));
        lblQLNhanVien_DanhSach.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblQLNhanVien_DanhSach.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLNhanVien_DanhSach.setText("Danh sách");
        lblQLNhanVien_DanhSach.setOpaque(true);
        lblQLNhanVien_DanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLNhanVien_DanhSachMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLNhanVien_DanhSachMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLNhanVien_DanhSachMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlQLNhanVien_HeadLayout = new javax.swing.GroupLayout(pnlQLNhanVien_Head);
        pnlQLNhanVien_Head.setLayout(pnlQLNhanVien_HeadLayout);
        pnlQLNhanVien_HeadLayout.setHorizontalGroup(
            pnlQLNhanVien_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLNhanVien_HeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLNhanVien_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlQLNhanVien_HeadLayout.createSequentialGroup()
                .addComponent(lblQLNhanVien_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblQLNhanVien_DanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlQLNhanVien_HeadLayout.setVerticalGroup(
            pnlQLNhanVien_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLNhanVien_HeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQLNhanVien_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlQLNhanVien_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblQLNhanVien_CapNhat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQLNhanVien_DanhSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlQLNhanVien_Body.setBackground(new java.awt.Color(255, 255, 255));
        pnlQLNhanVien_Body.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        pnlQLNhanVien_Body.setLayout(new java.awt.CardLayout());

        pnlQLNhanVien_CapNhat.setBackground(new java.awt.Color(255, 255, 255));

        lbl6A_MaNhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6A_MaNhanVien.setText("Mã nhân viên");

        txt6A_MaNhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl6A_HoTen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6A_HoTen.setText("Họ tên");

        txt6A_HoTen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl6A_GioiTinh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6A_GioiTinh.setText("Giới tính");

        cbo6A_GioiTinh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo6A_GioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        lbl6A_NgaySinh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6A_NgaySinh.setText("Ngày sinh (20-10-2021)");

        txt6A_NgaySinh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl6A_SoDienThoai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6A_SoDienThoai.setText("Số điện thoại");

        txt6A_SoDienThoai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl6A_Email.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6A_Email.setText("Email");

        txt6A_Email.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl6A_DiaChi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6A_DiaChi.setText("Địa chỉ");

        txt6A_DiaChi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl6A_Anh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6A_Anh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl6A_Anh.setText("Ảnh");
        lbl6A_Anh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lbl6A_Anh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl6A_AnhMouseClicked(evt);
            }
        });

        lbl6A_CCCD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6A_CCCD.setText("Căn cước công dân");

        txt6A_CCCD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lbl6A_ChucVu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6A_ChucVu.setText("Chức vụ");

        cbo6A_ChucVu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo6A_ChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trưởng phòng", "Nhân viên" }));

        lbl6A_MatKhau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6A_MatKhau.setText("Mật khẩu");

        pnl6A_MatKhau.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl6A_HienMK.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6A_HienMK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl6A_HienMK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/lblAn.PNG"))); // NOI18N
        lbl6A_HienMK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl6A_HienMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl6A_HienMKMouseClicked(evt);
            }
        });
        pnl6A_MatKhau.add(lbl6A_HienMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 40, 40));
        pnl6A_MatKhau.add(pwd6A_MatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 40));

        btn6A_Truoc.setBackground(new java.awt.Color(74, 220, 255));
        btn6A_Truoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn6A_Truoc.setForeground(new java.awt.Color(255, 255, 255));
        btn6A_Truoc.setText("|<");
        btn6A_Truoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6A_TruocActionPerformed(evt);
            }
        });

        btn6A_VeTruoc.setBackground(new java.awt.Color(74, 220, 255));
        btn6A_VeTruoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn6A_VeTruoc.setForeground(new java.awt.Color(255, 255, 255));
        btn6A_VeTruoc.setText("<<");
        btn6A_VeTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6A_VeTruocActionPerformed(evt);
            }
        });

        btn6A_VeSau.setBackground(new java.awt.Color(74, 220, 255));
        btn6A_VeSau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn6A_VeSau.setForeground(new java.awt.Color(255, 255, 255));
        btn6A_VeSau.setText(">>");
        btn6A_VeSau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6A_VeSauActionPerformed(evt);
            }
        });

        btn6A_Sau.setBackground(new java.awt.Color(74, 220, 255));
        btn6A_Sau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn6A_Sau.setForeground(new java.awt.Color(255, 255, 255));
        btn6A_Sau.setText(">|");
        btn6A_Sau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6A_SauActionPerformed(evt);
            }
        });

        btn6A_Them.setBackground(new java.awt.Color(74, 220, 255));
        btn6A_Them.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn6A_Them.setForeground(new java.awt.Color(255, 255, 255));
        btn6A_Them.setText("Thêm");
        btn6A_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6A_ThemActionPerformed(evt);
            }
        });

        btn6A_Sua.setBackground(new java.awt.Color(153, 153, 153));
        btn6A_Sua.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn6A_Sua.setForeground(new java.awt.Color(51, 255, 255));
        btn6A_Sua.setText("Sửa");
        btn6A_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6A_SuaActionPerformed(evt);
            }
        });

        btn6A_Xoa.setBackground(new java.awt.Color(153, 153, 153));
        btn6A_Xoa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn6A_Xoa.setForeground(new java.awt.Color(255, 0, 0));
        btn6A_Xoa.setText("Xóa");
        btn6A_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6A_XoaActionPerformed(evt);
            }
        });

        btn6A_LamMoi.setBackground(new java.awt.Color(74, 220, 255));
        btn6A_LamMoi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn6A_LamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btn6A_LamMoi.setText("Làm mới");
        btn6A_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6A_LamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLNhanVien_CapNhatLayout = new javax.swing.GroupLayout(pnlQLNhanVien_CapNhat);
        pnlQLNhanVien_CapNhat.setLayout(pnlQLNhanVien_CapNhatLayout);
        pnlQLNhanVien_CapNhatLayout.setHorizontalGroup(
            pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                        .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt6A_MaNhanVien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl6A_MaNhanVien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl6A_HoTen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl6A_GioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt6A_NgaySinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl6A_NgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo6A_GioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt6A_HoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt6A_DiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl6A_DiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                                .addGap(168, 168, 168)
                                .addComponent(lbl6A_Anh, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                        .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt6A_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl6A_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt6A_SoDienThoai, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbl6A_SoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt6A_CCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl6A_CCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                                .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbo6A_ChucVu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl6A_ChucVu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl6A_MatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pnl6A_MatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                        .addComponent(btn6A_Truoc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn6A_VeTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn6A_VeSau, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn6A_Sau, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn6A_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn6A_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn6A_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn6A_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlQLNhanVien_CapNhatLayout.setVerticalGroup(
            pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                        .addComponent(lbl6A_DiaChi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt6A_DiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                        .addComponent(lbl6A_MaNhanVien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt6A_MaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                        .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                                .addComponent(lbl6A_HoTen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt6A_HoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl6A_GioiTinh))
                            .addGroup(pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(cbo6A_GioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl6A_NgaySinh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt6A_NgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl6A_Anh, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                        .addComponent(lbl6A_SoDienThoai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt6A_SoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl6A_Email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt6A_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlQLNhanVien_CapNhatLayout.createSequentialGroup()
                        .addComponent(lbl6A_CCCD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt6A_CCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl6A_ChucVu)
                            .addComponent(lbl6A_MatKhau))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbo6A_ChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnl6A_MatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(32, 32, 32)
                .addGroup(pnlQLNhanVien_CapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn6A_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn6A_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn6A_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn6A_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn6A_Sau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn6A_VeSau, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn6A_VeTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn6A_Truoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78))
        );

        pnlQLNhanVien_Body.add(pnlQLNhanVien_CapNhat, "capnhat");

        pnlQLNhanVien_DanhSach.setBackground(new java.awt.Color(255, 255, 255));

        lbl6B_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl6B_TimKiem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl6B_TimKiem.setText("Tìm kiếm");
        lbl6B_TimKiem.setMaximumSize(new java.awt.Dimension(90, 40));
        lbl6B_TimKiem.setMinimumSize(new java.awt.Dimension(90, 40));
        lbl6B_TimKiem.setPreferredSize(new java.awt.Dimension(90, 40));

        txt6B_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt6B_TimKiem.setMaximumSize(new java.awt.Dimension(740, 40));
        txt6B_TimKiem.setMinimumSize(new java.awt.Dimension(740, 40));
        txt6B_TimKiem.setPreferredSize(new java.awt.Dimension(740, 40));
        txt6B_TimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt6B_TimKiemKeyReleased(evt);
            }
        });

        tblQLNhanVien.setAutoCreateRowSorter(true);
        tblQLNhanVien.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblQLNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"NV01", "Lê Tú Cầu", "Nam", "01/01/2002", "0789789789", "cault11@gmail.com", "Cần Thơ", "...", "012345678987", "Trưởng phòng"},
                {"...", "...", "...", "...", "...", "...", "...", "...", "...", "..."}
            },
            new String [] {
                "Mã nhân viên", "Họ tên", "Giới tính", "Ngày sinh", "SĐT", "Email", "Địa chỉ", "Ảnh", "CCCD", "Chức vụ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQLNhanVien.setRowHeight(40);
        tblQLNhanVien.setSelectionBackground(new java.awt.Color(200, 248, 255));
        tblQLNhanVien.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblQLNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLNhanVienMouseClicked(evt);
            }
        });
        src6B.setViewportView(tblQLNhanVien);
        if (tblQLNhanVien.getColumnModel().getColumnCount() > 0) {
            tblQLNhanVien.getColumnModel().getColumn(0).setResizable(false);
            tblQLNhanVien.getColumnModel().getColumn(1).setResizable(false);
            tblQLNhanVien.getColumnModel().getColumn(2).setResizable(false);
            tblQLNhanVien.getColumnModel().getColumn(3).setResizable(false);
            tblQLNhanVien.getColumnModel().getColumn(4).setResizable(false);
            tblQLNhanVien.getColumnModel().getColumn(5).setResizable(false);
            tblQLNhanVien.getColumnModel().getColumn(6).setResizable(false);
            tblQLNhanVien.getColumnModel().getColumn(7).setResizable(false);
            tblQLNhanVien.getColumnModel().getColumn(8).setResizable(false);
            tblQLNhanVien.getColumnModel().getColumn(9).setResizable(false);
        }

        javax.swing.GroupLayout pnlQLNhanVien_DanhSachLayout = new javax.swing.GroupLayout(pnlQLNhanVien_DanhSach);
        pnlQLNhanVien_DanhSach.setLayout(pnlQLNhanVien_DanhSachLayout);
        pnlQLNhanVien_DanhSachLayout.setHorizontalGroup(
            pnlQLNhanVien_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLNhanVien_DanhSachLayout.createSequentialGroup()
                .addGroup(pnlQLNhanVien_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLNhanVien_DanhSachLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(src6B, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlQLNhanVien_DanhSachLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(lbl6B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt6B_TimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(129, 129, 129)))
                .addGap(29, 29, 29))
        );
        pnlQLNhanVien_DanhSachLayout.setVerticalGroup(
            pnlQLNhanVien_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLNhanVien_DanhSachLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlQLNhanVien_DanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt6B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl6B_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(src6B, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );

        pnlQLNhanVien_Body.add(pnlQLNhanVien_DanhSach, "danhsach");

        javax.swing.GroupLayout pnlQLNhanVien_6Layout = new javax.swing.GroupLayout(pnlQLNhanVien_6);
        pnlQLNhanVien_6.setLayout(pnlQLNhanVien_6Layout);
        pnlQLNhanVien_6Layout.setHorizontalGroup(
            pnlQLNhanVien_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLNhanVien_6Layout.createSequentialGroup()
                .addGap(0, 90, Short.MAX_VALUE)
                .addGroup(pnlQLNhanVien_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlQLNhanVien_Head, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlQLNhanVien_Body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );
        pnlQLNhanVien_6Layout.setVerticalGroup(
            pnlQLNhanVien_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLNhanVien_6Layout.createSequentialGroup()
                .addComponent(pnlQLNhanVien_Head, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlQLNhanVien_Body, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
        );

        javax.swing.GroupLayout pnlQLNhanVienLayout = new javax.swing.GroupLayout(pnlQLNhanVien);
        pnlQLNhanVien.setLayout(pnlQLNhanVienLayout);
        pnlQLNhanVienLayout.setHorizontalGroup(
            pnlQLNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLNhanVien_6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlQLNhanVienLayout.setVerticalGroup(
            pnlQLNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLNhanVien_6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlNoiDung_0.add(pnlQLNhanVien, "qlnhanvien");

        pnlTKTongHop.setOpaque(false);

        pnlTKTongHop_7.setOpaque(false);

        pnlTKTongHop_Head.setOpaque(false);

        lblTKTongHop_Title.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTKTongHop_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTKTongHop_Title.setText("THỐNG KÊ TỔNG HỢP");

        lblTK1.setBackground(new java.awt.Color(242, 242, 242));
        lblTK1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTK1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTK1.setText("Người học đăng ký");
        lblTK1.setOpaque(true);
        lblTK1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTK1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTK1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTK1MouseExited(evt);
            }
        });

        lblTK2.setBackground(new java.awt.Color(242, 242, 242));
        lblTK2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTK2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTK2.setText("Điểm từng khóa học");
        lblTK2.setOpaque(true);
        lblTK2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTK2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTK2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTK2MouseExited(evt);
            }
        });

        lblTK3.setBackground(new java.awt.Color(242, 242, 242));
        lblTK3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTK3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTK3.setText("Điểm từng chuyên đề");
        lblTK3.setOpaque(true);
        lblTK3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTK3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTK3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTK3MouseExited(evt);
            }
        });

        lblTK4.setBackground(new java.awt.Color(242, 242, 242));
        lblTK4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTK4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTK4.setText("Doanh thu từng chuyên đề");
        lblTK4.setOpaque(true);
        lblTK4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTK4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTK4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTK4MouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlTKTongHop_HeadLayout = new javax.swing.GroupLayout(pnlTKTongHop_Head);
        pnlTKTongHop_Head.setLayout(pnlTKTongHop_HeadLayout);
        pnlTKTongHop_HeadLayout.setHorizontalGroup(
            pnlTKTongHop_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTKTongHop_HeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTKTongHop_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlTKTongHop_HeadLayout.createSequentialGroup()
                .addComponent(lblTK1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblTK2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblTK3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblTK4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlTKTongHop_HeadLayout.setVerticalGroup(
            pnlTKTongHop_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTKTongHop_HeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTKTongHop_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTKTongHop_HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTK1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTK2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTK3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTK4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlTKTongHop_Body.setBackground(new java.awt.Color(255, 255, 255));
        pnlTKTongHop_Body.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        pnlTKTongHop_Body.setLayout(new java.awt.CardLayout());

        pnlTK1.setBackground(new java.awt.Color(255, 255, 255));

        lbl7A_ThoiGian.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl7A_ThoiGian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl7A_ThoiGian.setText("THỜI GIAN");

        cbo7A_ThoiGian.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo7A_ThoiGian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Năm", "Năm Quý", "Năm Tháng" }));
        cbo7A_ThoiGian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo7A_ThoiGianActionPerformed(evt);
            }
        });

        tblTKNguoiHoc.setAutoCreateRowSorter(true);
        tblTKNguoiHoc.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblTKNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"2021", "40", "01/06/2021", "30/09/2021"},
                {"...", "...", "...", "..."}
            },
            new String [] {
                "Năm", "Số lượng người học", "Ngày đăng ký đầu tiên", "Ngày đăng ký sau cùng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTKNguoiHoc.setRowHeight(40);
        tblTKNguoiHoc.setSelectionBackground(new java.awt.Color(200, 248, 255));
        tblTKNguoiHoc.setSelectionForeground(new java.awt.Color(0, 0, 0));
        src7A.setViewportView(tblTKNguoiHoc);
        if (tblTKNguoiHoc.getColumnModel().getColumnCount() > 0) {
            tblTKNguoiHoc.getColumnModel().getColumn(0).setResizable(false);
            tblTKNguoiHoc.getColumnModel().getColumn(1).setResizable(false);
            tblTKNguoiHoc.getColumnModel().getColumn(2).setResizable(false);
            tblTKNguoiHoc.getColumnModel().getColumn(3).setResizable(false);
        }

        btn7A_Xuat.setBackground(new java.awt.Color(74, 220, 255));
        btn7A_Xuat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn7A_Xuat.setForeground(new java.awt.Color(255, 255, 255));
        btn7A_Xuat.setText("Xuất dữ liệu");
        btn7A_Xuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7A_XuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTK1Layout = new javax.swing.GroupLayout(pnlTK1);
        pnlTK1.setLayout(pnlTK1Layout);
        pnlTK1Layout.setHorizontalGroup(
            pnlTK1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTK1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlTK1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTK1Layout.createSequentialGroup()
                        .addComponent(lbl7A_ThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbo7A_ThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlTK1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn7A_Xuat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(src7A, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        pnlTK1Layout.setVerticalGroup(
            pnlTK1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTK1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlTK1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl7A_ThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo7A_ThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(src7A, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn7A_Xuat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pnlTKTongHop_Body.add(pnlTK1, "tk1");

        pnlTK2.setBackground(new java.awt.Color(255, 255, 255));

        lbl7B_ChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl7B_ChuyenDe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl7B_ChuyenDe.setText("CHUYÊN ĐỀ");

        cbo7B_ChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo7B_ChuyenDe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lập trình Java cơ bản" }));
        cbo7B_ChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo7B_ChuyenDeActionPerformed(evt);
            }
        });

        lbl7B_KhoaHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl7B_KhoaHoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl7B_KhoaHoc.setText("KHÓA HỌC");

        cbo7B_KhoaHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo7B_KhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Khóa 1" }));
        cbo7B_KhoaHoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo7B_KhoaHocItemStateChanged(evt);
            }
        });
        cbo7B_KhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo7B_KhoaHocActionPerformed(evt);
            }
        });

        tblTKDiem_KhoaHoc.setAutoCreateRowSorter(true);
        tblTKDiem_KhoaHoc.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblTKDiem_KhoaHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PC01234", "Nguyễn Văn Chính", "9.9", "Xuất sắc"},
                {"...", "...", "...", "..."}
            },
            new String [] {
                "Mã người học", "Họ tên", "Điểm trung bình", "Học lực"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTKDiem_KhoaHoc.setRowHeight(40);
        tblTKDiem_KhoaHoc.setSelectionBackground(new java.awt.Color(200, 248, 255));
        tblTKDiem_KhoaHoc.setSelectionForeground(new java.awt.Color(0, 0, 0));
        src7B.setViewportView(tblTKDiem_KhoaHoc);
        if (tblTKDiem_KhoaHoc.getColumnModel().getColumnCount() > 0) {
            tblTKDiem_KhoaHoc.getColumnModel().getColumn(0).setResizable(false);
            tblTKDiem_KhoaHoc.getColumnModel().getColumn(1).setResizable(false);
            tblTKDiem_KhoaHoc.getColumnModel().getColumn(2).setResizable(false);
            tblTKDiem_KhoaHoc.getColumnModel().getColumn(3).setResizable(false);
        }

        btn7B_Xuat.setBackground(new java.awt.Color(74, 220, 255));
        btn7B_Xuat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn7B_Xuat.setForeground(new java.awt.Color(255, 255, 255));
        btn7B_Xuat.setText("Xuất dữ liệu");
        btn7B_Xuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7B_XuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTK2Layout = new javax.swing.GroupLayout(pnlTK2);
        pnlTK2.setLayout(pnlTK2Layout);
        pnlTK2Layout.setHorizontalGroup(
            pnlTK2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTK2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlTK2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlTK2Layout.createSequentialGroup()
                        .addComponent(lbl7B_ChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbo7B_ChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl7B_KhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbo7B_KhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlTK2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn7B_Xuat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(src7B, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        pnlTK2Layout.setVerticalGroup(
            pnlTK2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTK2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlTK2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl7B_ChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo7B_ChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo7B_KhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl7B_KhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(src7B, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn7B_Xuat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pnlTKTongHop_Body.add(pnlTK2, "tk2");

        pnlTK3.setBackground(new java.awt.Color(255, 255, 255));

        lbl7C_Nam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl7C_Nam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl7C_Nam.setText("NĂM");

        cbo7C_Nam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo7C_Nam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2021", "2020" }));
        cbo7C_Nam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo7C_NamActionPerformed(evt);
            }
        });

        tblTKDiem_ChuyenDe.setAutoCreateRowSorter(true);
        tblTKDiem_ChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblTKDiem_ChuyenDe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Lập trình Java cơ bản", "40", "7.4", "9.9", "9.3"},
                {"...", "...", "...", "...", "..."}
            },
            new String [] {
                "Tên chuyên đề", "Số học viên", "Điểm thấp nhất", "Điểm cao nhất", "Điểm trung bình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTKDiem_ChuyenDe.setRowHeight(40);
        tblTKDiem_ChuyenDe.setSelectionBackground(new java.awt.Color(200, 248, 255));
        tblTKDiem_ChuyenDe.setSelectionForeground(new java.awt.Color(0, 0, 0));
        scr7C.setViewportView(tblTKDiem_ChuyenDe);
        if (tblTKDiem_ChuyenDe.getColumnModel().getColumnCount() > 0) {
            tblTKDiem_ChuyenDe.getColumnModel().getColumn(0).setResizable(false);
            tblTKDiem_ChuyenDe.getColumnModel().getColumn(1).setResizable(false);
            tblTKDiem_ChuyenDe.getColumnModel().getColumn(2).setResizable(false);
            tblTKDiem_ChuyenDe.getColumnModel().getColumn(3).setResizable(false);
            tblTKDiem_ChuyenDe.getColumnModel().getColumn(4).setResizable(false);
        }

        btn7C_Xuat.setBackground(new java.awt.Color(74, 220, 255));
        btn7C_Xuat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn7C_Xuat.setForeground(new java.awt.Color(255, 255, 255));
        btn7C_Xuat.setText("Xuất dữ liệu");
        btn7C_Xuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7C_XuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTK3Layout = new javax.swing.GroupLayout(pnlTK3);
        pnlTK3.setLayout(pnlTK3Layout);
        pnlTK3Layout.setHorizontalGroup(
            pnlTK3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTK3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlTK3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTK3Layout.createSequentialGroup()
                        .addComponent(lbl7C_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbo7C_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlTK3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn7C_Xuat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(scr7C, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        pnlTK3Layout.setVerticalGroup(
            pnlTK3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTK3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlTK3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl7C_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo7C_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scr7C, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn7C_Xuat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pnlTKTongHop_Body.add(pnlTK3, "tk3");

        pnlTK4.setBackground(new java.awt.Color(255, 255, 255));

        lbl7D_Nam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl7D_Nam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl7D_Nam.setText("NĂM");

        cbo7D_Nam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo7D_Nam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2021", "2020" }));
        cbo7D_Nam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo7D_NamActionPerformed(evt);
            }
        });

        tblTKDoanhThu_ChuyenDe.setAutoCreateRowSorter(true);
        tblTKDoanhThu_ChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblTKDoanhThu_ChuyenDe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Lập trình Java cơ bản", "2", "40", "200000000", "5000000", "5000000", "5000000"},
                {"...", "...", "...", "...", "...", "...", "..."}
            },
            new String [] {
                "Tên chuyên đề", "Số khóa học", "Tổng số học viên", "Doanh thu", "Học phí thấp nhất", "Học phí cao nhất", "Học phí trung bình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTKDoanhThu_ChuyenDe.setRowHeight(40);
        tblTKDoanhThu_ChuyenDe.setSelectionBackground(new java.awt.Color(200, 248, 255));
        tblTKDoanhThu_ChuyenDe.setSelectionForeground(new java.awt.Color(0, 0, 0));
        scr7D.setViewportView(tblTKDoanhThu_ChuyenDe);
        if (tblTKDoanhThu_ChuyenDe.getColumnModel().getColumnCount() > 0) {
            tblTKDoanhThu_ChuyenDe.getColumnModel().getColumn(0).setResizable(false);
            tblTKDoanhThu_ChuyenDe.getColumnModel().getColumn(1).setResizable(false);
            tblTKDoanhThu_ChuyenDe.getColumnModel().getColumn(2).setResizable(false);
            tblTKDoanhThu_ChuyenDe.getColumnModel().getColumn(3).setResizable(false);
            tblTKDoanhThu_ChuyenDe.getColumnModel().getColumn(4).setResizable(false);
            tblTKDoanhThu_ChuyenDe.getColumnModel().getColumn(5).setResizable(false);
            tblTKDoanhThu_ChuyenDe.getColumnModel().getColumn(6).setResizable(false);
        }

        btn7D_Xuat.setBackground(new java.awt.Color(74, 220, 255));
        btn7D_Xuat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn7D_Xuat.setForeground(new java.awt.Color(255, 255, 255));
        btn7D_Xuat.setText("Xuất dữ liệu");
        btn7D_Xuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7D_XuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTK4Layout = new javax.swing.GroupLayout(pnlTK4);
        pnlTK4.setLayout(pnlTK4Layout);
        pnlTK4Layout.setHorizontalGroup(
            pnlTK4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTK4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlTK4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTK4Layout.createSequentialGroup()
                        .addComponent(lbl7D_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbo7D_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlTK4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn7D_Xuat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(scr7D, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        pnlTK4Layout.setVerticalGroup(
            pnlTK4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTK4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlTK4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl7D_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo7D_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scr7D, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn7D_Xuat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pnlTKTongHop_Body.add(pnlTK4, "tk4");

        javax.swing.GroupLayout pnlTKTongHop_7Layout = new javax.swing.GroupLayout(pnlTKTongHop_7);
        pnlTKTongHop_7.setLayout(pnlTKTongHop_7Layout);
        pnlTKTongHop_7Layout.setHorizontalGroup(
            pnlTKTongHop_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTKTongHop_7Layout.createSequentialGroup()
                .addGap(0, 90, Short.MAX_VALUE)
                .addGroup(pnlTKTongHop_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlTKTongHop_Head, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTKTongHop_Body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );
        pnlTKTongHop_7Layout.setVerticalGroup(
            pnlTKTongHop_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTKTongHop_7Layout.createSequentialGroup()
                .addComponent(pnlTKTongHop_Head, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlTKTongHop_Body, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlTKTongHopLayout = new javax.swing.GroupLayout(pnlTKTongHop);
        pnlTKTongHop.setLayout(pnlTKTongHopLayout);
        pnlTKTongHopLayout.setHorizontalGroup(
            pnlTKTongHopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTKTongHop_7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTKTongHopLayout.setVerticalGroup(
            pnlTKTongHopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTKTongHop_7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlNoiDung_0.add(pnlTKTongHop, "tktonghop");

        pnlNoiDung.add(pnlNoiDung_0, new org.netbeans.lib.awtextra.AbsoluteConstraints(-14, 69, 1310, 790));

        lblAnhNen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/UI/bkg1.JPG"))); // NOI18N
        pnlNoiDung.add(lblAnhNen, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, -3, 1300, 860));

        mnuHeThong.setBackground(new Color(0,0,0,1));
        mnuHeThong.setText("Hệ thống");
        mnuHeThong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mnuHeThong.setMargin(new java.awt.Insets(10, 20, 10, 20));

        mniDoiMatKhau.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.ALT_MASK));
        mniDoiMatKhau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/doimatkhau.png"))); // NOI18N
        mniDoiMatKhau.setText("  Đổi mật khẩu");
        mniDoiMatKhau.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDoiMatKhauActionPerformed(evt);
            }
        });
        mnuHeThong.add(mniDoiMatKhau);
        mnuHeThong.add(sprA1);

        mniDangXuat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, java.awt.event.InputEvent.ALT_MASK));
        mniDangXuat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/dangxuat.png"))); // NOI18N
        mniDangXuat.setText("  Đăng xuất");
        mniDangXuat.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDangXuatActionPerformed(evt);
            }
        });
        mnuHeThong.add(mniDangXuat);

        mniThoat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mniThoat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/thoat.png"))); // NOI18N
        mniThoat.setText("  Thoát");
        mniThoat.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniThoatActionPerformed(evt);
            }
        });
        mnuHeThong.add(mniThoat);

        mnubar.add(mnuHeThong);

        mnuQuanLy.setBackground(new Color(0,0,0,1));
        mnuQuanLy.setText("Quản lý");
        mnuQuanLy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mnuQuanLy.setMargin(new java.awt.Insets(10, 20, 10, 20));

        mniQLChuyenDe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        mniQLChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniQLChuyenDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/chuyende.png"))); // NOI18N
        mniQLChuyenDe.setText("  Chuyên đề");
        mniQLChuyenDe.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniQLChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniQLChuyenDeActionPerformed(evt);
            }
        });
        mnuQuanLy.add(mniQLChuyenDe);

        mniQLKhHoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        mniQLKhHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniQLKhHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/khoahoc.png"))); // NOI18N
        mniQLKhHoc.setText("  Khóa học");
        mniQLKhHoc.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniQLKhHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniQLKhHocActionPerformed(evt);
            }
        });
        mnuQuanLy.add(mniQLKhHoc);
        mnuQuanLy.add(sprB1);

        mniQLNgHoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_MASK));
        mniQLNgHoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniQLNgHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/nguoihoc.png"))); // NOI18N
        mniQLNgHoc.setText("  Người học");
        mniQLNgHoc.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniQLNgHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniQLNgHocActionPerformed(evt);
            }
        });
        mnuQuanLy.add(mniQLNgHoc);

        mniQLHoVien.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.CTRL_MASK));
        mniQLHoVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniQLHoVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/hocvien.png"))); // NOI18N
        mniQLHoVien.setText("  Học viên");
        mniQLHoVien.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniQLHoVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniQLHoVienActionPerformed(evt);
            }
        });
        mnuQuanLy.add(mniQLHoVien);
        mnuQuanLy.add(sprB2);

        mniNhVien.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_5, java.awt.event.InputEvent.CTRL_MASK));
        mniNhVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniNhVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/nhanvien.png"))); // NOI18N
        mniNhVien.setText("  Nhân viên");
        mniNhVien.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniNhVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniNhVienActionPerformed(evt);
            }
        });
        mnuQuanLy.add(mniNhVien);

        mnubar.add(mnuQuanLy);

        mnuThongKe.setBackground(new Color(0,0,0,1));
        mnuThongKe.setText("Thống kê");
        mnuThongKe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mnuThongKe.setMargin(new java.awt.Insets(10, 20, 10, 20));

        mniTK1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mniTK1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniTK1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/thongke1.png"))); // NOI18N
        mniTK1.setText("  Người học đăng ký");
        mniTK1.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniTK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTK1ActionPerformed(evt);
            }
        });
        mnuThongKe.add(mniTK1);

        mniTK2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mniTK2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniTK2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/thongke2.png"))); // NOI18N
        mniTK2.setText("  Điểm từng khóa Học");
        mniTK2.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniTK2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTK2ActionPerformed(evt);
            }
        });
        mnuThongKe.add(mniTK2);

        mniTK3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mniTK3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniTK3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/thongke3.png"))); // NOI18N
        mniTK3.setText("  Điểm từng chuyên đề");
        mniTK3.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniTK3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTK3ActionPerformed(evt);
            }
        });
        mnuThongKe.add(mniTK3);

        mniTK4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mniTK4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniTK4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/thongke4.png"))); // NOI18N
        mniTK4.setText("  Doanh thu từng chuyên đề");
        mniTK4.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniTK4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTK4ActionPerformed(evt);
            }
        });
        mnuThongKe.add(mniTK4);

        mnubar.add(mnuThongKe);

        mnuTroGiup.setBackground(new Color(0,0,0,1));
        mnuTroGiup.setText("Trợ giúp");
        mnuTroGiup.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mnuTroGiup.setMargin(new java.awt.Insets(10, 20, 10, 20));

        mniHuongDan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        mniHuongDan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniHuongDan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/huongdan.png"))); // NOI18N
        mniHuongDan.setText("  Hướng dẫn");
        mniHuongDan.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniHuongDan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHuongDanActionPerformed(evt);
            }
        });
        mnuTroGiup.add(mniHuongDan);

        mniGioiThieu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        mniGioiThieu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/gioithieu.png"))); // NOI18N
        mniGioiThieu.setText("  Giới thiệu");
        mniGioiThieu.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniGioiThieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniGioiThieuActionPerformed(evt);
            }
        });
        mnuTroGiup.add(mniGioiThieu);

        mniBaoCao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        mniBaoCao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mniBaoCao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icon/36x36/baocao.png"))); // NOI18N
        mniBaoCao.setText("  Báo cáo");
        mniBaoCao.setMargin(new java.awt.Insets(10, 10, 10, 10));
        mniBaoCao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniBaoCaoActionPerformed(evt);
            }
        });
        mnuTroGiup.add(mniBaoCao);

        mnubar.add(mnuTroGiup);

        setJMenuBar(mnubar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlDieuHuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlNoiDung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDieuHuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlNoiDung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1618, 947));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblTrangChuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMouseEntered
        doiMau_Menu_Enter(lblTrangChu);
    }//GEN-LAST:event_lblTrangChuMouseEntered

    private void lblTrangChuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMouseExited
        doiMau_Menu_Exit(lblTrangChu);
    }//GEN-LAST:event_lblTrangChuMouseExited

    private void lblQLChuyenDeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLChuyenDeMouseEntered
        doiMau_Menu_Enter(lblQLChuyenDe);
    }//GEN-LAST:event_lblQLChuyenDeMouseEntered

    private void lblQLChuyenDeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLChuyenDeMouseExited
        doiMau_Menu_Exit(lblQLChuyenDe);
    }//GEN-LAST:event_lblQLChuyenDeMouseExited

    private void lblQLKhoaHocMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLKhoaHocMouseEntered
        doiMau_Menu_Enter(lblQLKhoaHoc);
    }//GEN-LAST:event_lblQLKhoaHocMouseEntered

    private void lblQLKhoaHocMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLKhoaHocMouseExited
        doiMau_Menu_Exit(lblQLKhoaHoc);
    }//GEN-LAST:event_lblQLKhoaHocMouseExited

    private void lblQLNguoiHocMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNguoiHocMouseEntered
        doiMau_Menu_Enter(lblQLNguoiHoc);
    }//GEN-LAST:event_lblQLNguoiHocMouseEntered

    private void lblQLNguoiHocMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNguoiHocMouseExited
        doiMau_Menu_Exit(lblQLNguoiHoc);
    }//GEN-LAST:event_lblQLNguoiHocMouseExited

    private void lblQLHocVienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLHocVienMouseEntered
        doiMau_Menu_Enter(lblQLHocVien);
    }//GEN-LAST:event_lblQLHocVienMouseEntered

    private void lblQLHocVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLHocVienMouseExited
        doiMau_Menu_Exit(lblQLHocVien);
    }//GEN-LAST:event_lblQLHocVienMouseExited

    private void lblQLNhanVienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNhanVienMouseEntered
        doiMau_Menu_Enter(lblQLNhanVien);
    }//GEN-LAST:event_lblQLNhanVienMouseEntered

    private void lblQLNhanVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNhanVienMouseExited
        doiMau_Menu_Exit(lblQLNhanVien);
    }//GEN-LAST:event_lblQLNhanVienMouseExited

    private void lblTKTongHopMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTKTongHopMouseEntered
        doiMau_Menu_Enter(lblTKTongHop);
    }//GEN-LAST:event_lblTKTongHopMouseEntered

    private void lblTKTongHopMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTKTongHopMouseExited
        doiMau_Menu_Exit(lblTKTongHop);
    }//GEN-LAST:event_lblTKTongHopMouseExited

    private void lblBaoCaoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBaoCaoMouseEntered
        doiMau_Menu_Enter(lblBaoCao);
    }//GEN-LAST:event_lblBaoCaoMouseEntered

    private void lblBaoCaoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBaoCaoMouseExited
        doiMau_Menu_Exit(lblBaoCao);
    }//GEN-LAST:event_lblBaoCaoMouseExited

    private void lblDangXuatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangXuatMouseEntered
        doiMau_Menu_Enter(lblDangXuat);
    }//GEN-LAST:event_lblDangXuatMouseEntered

    private void lblDangXuatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangXuatMouseExited
        doiMau_Menu_Exit(lblDangXuat);
    }//GEN-LAST:event_lblDangXuatMouseExited

    private void lblTrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrangChuMouseClicked
        doiMau_Menu_Click(lblTrangChu);
        chuyenTab(pnlNoiDung_0, "trangchu");
    }//GEN-LAST:event_lblTrangChuMouseClicked

    private void lblQLChuyenDeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLChuyenDeMouseClicked
        doiMau_Menu_Click(lblQLChuyenDe);
        chuyenTab(pnlNoiDung_0, "qlchuyende");
    }//GEN-LAST:event_lblQLChuyenDeMouseClicked

    private void lblQLKhoaHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLKhoaHocMouseClicked
        doiMau_Menu_Click(lblQLKhoaHoc);
        chuyenTab(pnlNoiDung_0, "qlkhoahoc");
    }//GEN-LAST:event_lblQLKhoaHocMouseClicked

    private void lblQLNguoiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNguoiHocMouseClicked
        doiMau_Menu_Click(lblQLNguoiHoc);
        chuyenTab(pnlNoiDung_0, "qlnguoihoc");
    }//GEN-LAST:event_lblQLNguoiHocMouseClicked

    private void lblQLHocVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLHocVienMouseClicked
        doiMau_Menu_Click(lblQLHocVien);
        chuyenTab(pnlNoiDung_0, "qlhocvien");
    }//GEN-LAST:event_lblQLHocVienMouseClicked

    private void lblQLNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNhanVienMouseClicked
        doiMau_Menu_Click(lblQLNhanVien);
        chuyenTab(pnlNoiDung_0, "qlnhanvien");
    }//GEN-LAST:event_lblQLNhanVienMouseClicked

    private void lblTKTongHopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTKTongHopMouseClicked
        doiMau_Menu_Click(lblTKTongHop);
        chuyenTab(pnlNoiDung_0, "tktonghop");
    }//GEN-LAST:event_lblTKTongHopMouseClicked

    private void lblBaoCaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBaoCaoMouseClicked
        hienthiFrame(JFrame_BaoCao);
    }//GEN-LAST:event_lblBaoCaoMouseClicked

    private void lblDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangXuatMouseClicked
        boolean thoat = MessageHelper.confirm(this, "ĐĂNG XUẤT CHƯƠNG TRÌNH?", "XÁC NHẬN", linkAnhHoi);
        if (thoat) {
            hienthiFrame(JFrame_DangNhap);
            dispose();
            JFrame_HuongDan.dispose();
            JFrame_BaoCao.dispose();
            JFrame_DoiMatKhau.dispose();
            JDialog_GioiThieu.dispose();
        }
    }//GEN-LAST:event_lblDangXuatMouseClicked

    private void lblQLChuyenDe_CapNhatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLChuyenDe_CapNhatMouseEntered
        doiMau_Menu_Enter(lblQLChuyenDe_CapNhat);
    }//GEN-LAST:event_lblQLChuyenDe_CapNhatMouseEntered

    private void lblQLChuyenDe_CapNhatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLChuyenDe_CapNhatMouseExited
        doiMau_Menu_Exit(lblQLChuyenDe_CapNhat);
    }//GEN-LAST:event_lblQLChuyenDe_CapNhatMouseExited

    private void lblQLChuyenDe_DanhSachMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLChuyenDe_DanhSachMouseEntered
        doiMau_Menu_Enter(lblQLChuyenDe_DanhSach);
    }//GEN-LAST:event_lblQLChuyenDe_DanhSachMouseEntered

    private void lblQLChuyenDe_DanhSachMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLChuyenDe_DanhSachMouseExited
        doiMau_Menu_Exit(lblQLChuyenDe_DanhSach);
    }//GEN-LAST:event_lblQLChuyenDe_DanhSachMouseExited

    private void lblQLKhoaHoc_CapNhatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLKhoaHoc_CapNhatMouseEntered
        doiMau_Menu_Enter(lblQLKhoaHoc_CapNhat);
    }//GEN-LAST:event_lblQLKhoaHoc_CapNhatMouseEntered

    private void lblQLKhoaHoc_CapNhatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLKhoaHoc_CapNhatMouseExited
        doiMau_Menu_Exit(lblQLKhoaHoc_CapNhat);
    }//GEN-LAST:event_lblQLKhoaHoc_CapNhatMouseExited

    private void lblQLKhoaHoc_DanhSachMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLKhoaHoc_DanhSachMouseEntered
        doiMau_Menu_Enter(lblQLKhoaHoc_DanhSach);
    }//GEN-LAST:event_lblQLKhoaHoc_DanhSachMouseEntered

    private void lblQLKhoaHoc_DanhSachMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLKhoaHoc_DanhSachMouseExited
        doiMau_Menu_Exit(lblQLKhoaHoc_DanhSach);
    }//GEN-LAST:event_lblQLKhoaHoc_DanhSachMouseExited

    private void lblQLNguoiHoc_CapNhatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNguoiHoc_CapNhatMouseEntered
        doiMau_Menu_Enter(lblQLNguoiHoc_CapNhat);
    }//GEN-LAST:event_lblQLNguoiHoc_CapNhatMouseEntered

    private void lblQLNguoiHoc_CapNhatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNguoiHoc_CapNhatMouseExited
        doiMau_Menu_Exit(lblQLNguoiHoc_CapNhat);
    }//GEN-LAST:event_lblQLNguoiHoc_CapNhatMouseExited

    private void lblQLNguoiHoc_DanhSachMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNguoiHoc_DanhSachMouseEntered
        doiMau_Menu_Enter(lblQLNguoiHoc_DanhSach);
    }//GEN-LAST:event_lblQLNguoiHoc_DanhSachMouseEntered

    private void lblQLNguoiHoc_DanhSachMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNguoiHoc_DanhSachMouseExited
        doiMau_Menu_Exit(lblQLNguoiHoc_DanhSach);
    }//GEN-LAST:event_lblQLNguoiHoc_DanhSachMouseExited

    private void lblQLHocVien_HocVienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLHocVien_HocVienMouseEntered
        doiMau_Menu_Enter(lblQLHocVien_HocVien);
    }//GEN-LAST:event_lblQLHocVien_HocVienMouseEntered

    private void lblQLHocVien_HocVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLHocVien_HocVienMouseExited
        doiMau_Menu_Exit(lblQLHocVien_HocVien);
    }//GEN-LAST:event_lblQLHocVien_HocVienMouseExited

    private void lblQLHocVien_NguoiHocMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLHocVien_NguoiHocMouseEntered
        doiMau_Menu_Enter(lblQLHocVien_NguoiHoc);
    }//GEN-LAST:event_lblQLHocVien_NguoiHocMouseEntered

    private void lblQLHocVien_NguoiHocMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLHocVien_NguoiHocMouseExited
        doiMau_Menu_Exit(lblQLHocVien_NguoiHoc);
    }//GEN-LAST:event_lblQLHocVien_NguoiHocMouseExited

    private void lblQLNhanVien_CapNhatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNhanVien_CapNhatMouseEntered
        doiMau_Menu_Enter(lblQLNhanVien_CapNhat);
    }//GEN-LAST:event_lblQLNhanVien_CapNhatMouseEntered

    private void lblQLNhanVien_CapNhatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNhanVien_CapNhatMouseExited
        doiMau_Menu_Exit(lblQLNhanVien_CapNhat);
    }//GEN-LAST:event_lblQLNhanVien_CapNhatMouseExited

    private void lblQLNhanVien_DanhSachMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNhanVien_DanhSachMouseEntered
        doiMau_Menu_Enter(lblQLNhanVien_DanhSach);
    }//GEN-LAST:event_lblQLNhanVien_DanhSachMouseEntered

    private void lblQLNhanVien_DanhSachMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNhanVien_DanhSachMouseExited
        doiMau_Menu_Exit(lblQLNhanVien_DanhSach);
    }//GEN-LAST:event_lblQLNhanVien_DanhSachMouseExited

    private void lblTK1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTK1MouseEntered
        doiMau_Menu_Enter(lblTK1);
    }//GEN-LAST:event_lblTK1MouseEntered

    private void lblTK1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTK1MouseExited
        doiMau_Menu_Exit(lblTK1);
    }//GEN-LAST:event_lblTK1MouseExited

    private void lblTK2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTK2MouseEntered
        doiMau_Menu_Enter(lblTK2);
    }//GEN-LAST:event_lblTK2MouseEntered

    private void lblTK2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTK2MouseExited
        doiMau_Menu_Exit(lblTK2);
    }//GEN-LAST:event_lblTK2MouseExited

    private void lblTK3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTK3MouseEntered
        doiMau_Menu_Enter(lblTK3);
    }//GEN-LAST:event_lblTK3MouseEntered

    private void lblTK3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTK3MouseExited
        doiMau_Menu_Exit(lblTK3);
    }//GEN-LAST:event_lblTK3MouseExited

    private void lblTK4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTK4MouseEntered
        doiMau_Menu_Enter(lblTK4);
    }//GEN-LAST:event_lblTK4MouseEntered

    private void lblTK4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTK4MouseExited
        doiMau_Menu_Exit(lblTK4);
    }//GEN-LAST:event_lblTK4MouseExited

    private void lblQLChuyenDe_CapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLChuyenDe_CapNhatMouseClicked
        doiMau_QLTK_Click(lblQLChuyenDe_CapNhat, lblQLChuyenDe_DanhSach);
        chuyenTab(pnlQLChuyenDe_Body, "capnhat");
    }//GEN-LAST:event_lblQLChuyenDe_CapNhatMouseClicked

    private void lblQLChuyenDe_DanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLChuyenDe_DanhSachMouseClicked
        doiMau_QLTK_Click(lblQLChuyenDe_DanhSach, lblQLChuyenDe_CapNhat);
        chuyenTab(pnlQLChuyenDe_Body, "danhsach");
    }//GEN-LAST:event_lblQLChuyenDe_DanhSachMouseClicked

    private void lblQLKhoaHoc_CapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLKhoaHoc_CapNhatMouseClicked
        doiMau_QLTK_Click(lblQLKhoaHoc_CapNhat, lblQLKhoaHoc_DanhSach);
        chuyenTab(pnlQLKhoaHoc_Body, "capnhat");
    }//GEN-LAST:event_lblQLKhoaHoc_CapNhatMouseClicked

    private void lblQLKhoaHoc_DanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLKhoaHoc_DanhSachMouseClicked
        doiMau_QLTK_Click(lblQLKhoaHoc_DanhSach, lblQLKhoaHoc_CapNhat);
        chuyenTab(pnlQLKhoaHoc_Body, "danhsach");
    }//GEN-LAST:event_lblQLKhoaHoc_DanhSachMouseClicked

    private void lblQLNguoiHoc_CapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNguoiHoc_CapNhatMouseClicked
        doiMau_QLTK_Click(lblQLNguoiHoc_CapNhat, lblQLNguoiHoc_DanhSach);
        chuyenTab(pnlQLNguoiHoc_Body, "capnhat");
    }//GEN-LAST:event_lblQLNguoiHoc_CapNhatMouseClicked

    private void lblQLNguoiHoc_DanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNguoiHoc_DanhSachMouseClicked
        doiMau_QLTK_Click(lblQLNguoiHoc_DanhSach, lblQLNguoiHoc_CapNhat);
        chuyenTab(pnlQLNguoiHoc_Body, "danhsach");
    }//GEN-LAST:event_lblQLNguoiHoc_DanhSachMouseClicked

    private void lblQLHocVien_HocVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLHocVien_HocVienMouseClicked
        doiMau_QLTK_Click(lblQLHocVien_HocVien, lblQLHocVien_NguoiHoc);
        chuyenTab(pnlQLHocVien_Body, "hocvien");
    }//GEN-LAST:event_lblQLHocVien_HocVienMouseClicked

    private void lblQLHocVien_NguoiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLHocVien_NguoiHocMouseClicked
        doiMau_QLTK_Click(lblQLHocVien_NguoiHoc, lblQLHocVien_HocVien);
        chuyenTab(pnlQLHocVien_Body, "nguoihoc");
    }//GEN-LAST:event_lblQLHocVien_NguoiHocMouseClicked

    private void lblQLNhanVien_CapNhatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNhanVien_CapNhatMouseClicked
        doiMau_QLTK_Click(lblQLNhanVien_CapNhat, lblQLNhanVien_DanhSach);
        chuyenTab(pnlQLNhanVien_Body, "capnhat");
    }//GEN-LAST:event_lblQLNhanVien_CapNhatMouseClicked

    private void lblQLNhanVien_DanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNhanVien_DanhSachMouseClicked
        doiMau_QLTK_Click(lblQLNhanVien_DanhSach, lblQLNhanVien_CapNhat);
        chuyenTab(pnlQLNhanVien_Body, "danhsach");
    }//GEN-LAST:event_lblQLNhanVien_DanhSachMouseClicked

    private void lblTK1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTK1MouseClicked
        doiMau_QLTK_Click(lblTK1, lblTK2);
        doiMau_QLTK_Click(lblTK1, lblTK3);
        doiMau_QLTK_Click(lblTK1, lblTK4);
        chuyenTab(pnlTKTongHop_Body, "tk1");
    }//GEN-LAST:event_lblTK1MouseClicked

    private void lblTK2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTK2MouseClicked
        doiMau_QLTK_Click(lblTK2, lblTK1);
        doiMau_QLTK_Click(lblTK2, lblTK3);
        doiMau_QLTK_Click(lblTK2, lblTK4);
        chuyenTab(pnlTKTongHop_Body, "tk2");
    }//GEN-LAST:event_lblTK2MouseClicked

    private void lblTK3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTK3MouseClicked
        doiMau_QLTK_Click(lblTK3, lblTK1);
        doiMau_QLTK_Click(lblTK3, lblTK2);
        doiMau_QLTK_Click(lblTK3, lblTK4);
        chuyenTab(pnlTKTongHop_Body, "tk3");
    }//GEN-LAST:event_lblTK3MouseClicked

    private void lblTK4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTK4MouseClicked
        doiMau_QLTK_Click(lblTK4, lblTK1);
        doiMau_QLTK_Click(lblTK4, lblTK2);
        doiMau_QLTK_Click(lblTK4, lblTK3);
        chuyenTab(pnlTKTongHop_Body, "tk4");
    }//GEN-LAST:event_lblTK4MouseClicked

    private void lblMoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMoreMouseClicked
        hienThiDialog(JDialog_GioiThieu);
    }//GEN-LAST:event_lblMoreMouseClicked

    private void btnB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB1ActionPerformed
        dangNhap();
    }//GEN-LAST:event_btnB1ActionPerformed

    private void pwdB1_MatKhauKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pwdB1_MatKhauKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            dangNhap();
        }
    }//GEN-LAST:event_pwdB1_MatKhauKeyReleased

    private void lblB1_HienMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB1_HienMKMouseClicked
        hienMatKhau(lblB1_HienMK, pwdB1_MatKhau);
    }//GEN-LAST:event_lblB1_HienMKMouseClicked

    private void lblB1_QuenMatKhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB1_QuenMatKhauMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            JFrame_DangNhap.setVisible(false);
            hienthiFrame(JFrame_QuenMatKhau);
        }
    }//GEN-LAST:event_lblB1_QuenMatKhauMouseClicked

    private void chkB1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkB1MouseClicked
        // 
    }//GEN-LAST:event_chkB1MouseClicked

    private void mniDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDoiMatKhauActionPerformed
        hienthiFrame(JFrame_DoiMatKhau);
    }//GEN-LAST:event_mniDoiMatKhauActionPerformed

    private void mniDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDangXuatActionPerformed
        boolean thoat = MessageHelper.confirm(this, "ĐĂNG XUẤT CHƯƠNG TRÌNH?", "XÁC NHẬN", linkAnhHoi);
        if (thoat) {
            hienthiFrame(JFrame_DangNhap);
            dispose();
            JFrame_HuongDan.dispose();
            JFrame_BaoCao.dispose();
            JFrame_DoiMatKhau.dispose();
            JDialog_GioiThieu.dispose();
        }
    }//GEN-LAST:event_mniDangXuatActionPerformed

    private void mniThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniThoatActionPerformed
        boolean thoat = MessageHelper.confirm(this, "THOÁT CHƯƠNG TRÌNH?", "XÁC NHẬN", linkAnhHoi);
        if (thoat) {
            System.exit(0);
        }
    }//GEN-LAST:event_mniThoatActionPerformed

    private void mniQLChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniQLChuyenDeActionPerformed
        lblQLChuyenDeMouseClicked(null);
    }//GEN-LAST:event_mniQLChuyenDeActionPerformed

    private void mniQLKhHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniQLKhHocActionPerformed
        lblQLKhoaHocMouseClicked(null);
    }//GEN-LAST:event_mniQLKhHocActionPerformed

    private void mniQLNgHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniQLNgHocActionPerformed
        lblQLNguoiHocMouseClicked(null);
    }//GEN-LAST:event_mniQLNgHocActionPerformed

    private void mniQLHoVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniQLHoVienActionPerformed
        lblQLHocVienMouseClicked(null);
    }//GEN-LAST:event_mniQLHoVienActionPerformed

    private void mniNhVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniNhVienActionPerformed
        lblQLNhanVienMouseClicked(null);
    }//GEN-LAST:event_mniNhVienActionPerformed

    private void mniTK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTK1ActionPerformed
        lblTKTongHopMouseClicked(null);
        lblTK1MouseClicked(null);
    }//GEN-LAST:event_mniTK1ActionPerformed

    private void mniTK2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTK2ActionPerformed
        lblTKTongHopMouseClicked(null);
        lblTK2MouseClicked(null);
    }//GEN-LAST:event_mniTK2ActionPerformed

    private void mniTK3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTK3ActionPerformed
        lblTKTongHopMouseClicked(null);
        lblTK3MouseClicked(null);
    }//GEN-LAST:event_mniTK3ActionPerformed

    private void mniTK4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTK4ActionPerformed
        lblTKTongHopMouseClicked(null);
        lblTK4MouseClicked(null);
    }//GEN-LAST:event_mniTK4ActionPerformed

    private void mniHuongDanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniHuongDanActionPerformed
        hienthiFrame(JFrame_HuongDan);
        huongDan("video/demo1.mp4");
    }//GEN-LAST:event_mniHuongDanActionPerformed

    private void mniGioiThieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniGioiThieuActionPerformed
        hienThiDialog(JDialog_GioiThieu);
    }//GEN-LAST:event_mniGioiThieuActionPerformed

    private void mniBaoCaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniBaoCaoActionPerformed
        hienthiFrame(JFrame_BaoCao);
    }//GEN-LAST:event_mniBaoCaoActionPerformed

    private void btnC1_GuiMaXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC1_GuiMaXacNhanActionPerformed
        try {
            maKhoiPhuc = 0;
            quenMatKhau();
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }//GEN-LAST:event_btnC1_GuiMaXacNhanActionPerformed

    private void lblC1_HienMKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC1_HienMKMMouseClicked
        hienMatKhau(lblC1_HienMKM, pwdC1_MatKhauMoi);
    }//GEN-LAST:event_lblC1_HienMKMMouseClicked

    private void lblC1_HienXNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC1_HienXNMouseClicked
        hienMatKhau(lblC1_HienXN, pwdC1_XacNhan);
    }//GEN-LAST:event_lblC1_HienXNMouseClicked

    private void btnC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnC1ActionPerformed
        try {
            quenMatKhau();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnC1ActionPerformed

    private void lblC1_ClearQMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC1_ClearQMKMouseClicked
        clearQuenMatKhau();
    }//GEN-LAST:event_lblC1_ClearQMKMouseClicked

    private void lblC1_QuayLaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC1_QuayLaiMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            JFrame_QuenMatKhau.setVisible(false);
            hienthiFrame(JFrame_DangNhap);
        }
    }//GEN-LAST:event_lblC1_QuayLaiMouseClicked

    private void lblD1_HienMKHTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD1_HienMKHTMouseClicked
        hienMatKhau(lblD1_HienMKHT, pwdD1_MatKhauHienTai);
    }//GEN-LAST:event_lblD1_HienMKHTMouseClicked

    private void lblD1_HienMKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD1_HienMKMMouseClicked
        hienMatKhau(lblD1_HienMKM, pwdD1_MatKhauMoi);
    }//GEN-LAST:event_lblD1_HienMKMMouseClicked

    private void lblD1_HienXNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD1_HienXNMouseClicked
        hienMatKhau(lblD1_HienXN, pwdD1_XacNhan);
    }//GEN-LAST:event_lblD1_HienXNMouseClicked

    private void btnD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnD1ActionPerformed
        doiMatKhau();
    }//GEN-LAST:event_btnD1ActionPerformed

    private void cbo7A_ThoiGianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo7A_ThoiGianActionPerformed
        if (cbo7A_ThoiGian.getSelectedIndex() == 0) {
            taiListTK1("nam");
        } else if (cbo7A_ThoiGian.getSelectedIndex() == 1) {
            taiListTK1("quy");
        } else {
            taiListTK1("thang");
        }
    }//GEN-LAST:event_cbo7A_ThoiGianActionPerformed

    private void btn7A_XuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7A_XuatActionPerformed
        xuatTK1();
    }//GEN-LAST:event_btn7A_XuatActionPerformed

    private void cbo7B_ChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo7B_ChuyenDeActionPerformed
        if (cbo7B_ChuyenDe.getItemCount() > 0) {
            List<Object[]> list = new ArrayList<>();
            cbo7B_KhoaHoc.removeAllItems();

            list = ThongKeDAO.cboKhoaHoc((String) listTK2_CD.get(cbo7B_ChuyenDe.getSelectedIndex())[1]);
            for (Object[] cbo : list) {
                cbo7B_KhoaHoc.addItem((String) cbo[0]);
            }
        }
    }//GEN-LAST:event_cbo7B_ChuyenDeActionPerformed

    private void cbo7B_KhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo7B_KhoaHocActionPerformed
        if (cbo7B_ChuyenDe.getItemCount() > 0) {
            taiListTK2((String) cbo7B_KhoaHoc.getSelectedItem());
        }
    }//GEN-LAST:event_cbo7B_KhoaHocActionPerformed

    private void cbo7B_KhoaHocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo7B_KhoaHocItemStateChanged
        if (cbo7B_ChuyenDe.getItemCount() > 0) {
            taiListTK2((String) cbo7B_KhoaHoc.getSelectedItem());
        }
    }//GEN-LAST:event_cbo7B_KhoaHocItemStateChanged

    private void btn7B_XuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7B_XuatActionPerformed
        xuatTK2();
    }//GEN-LAST:event_btn7B_XuatActionPerformed

    private void cbo7C_NamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo7C_NamActionPerformed
        if (cbo7C_Nam.getItemCount() > 0) {
            taiListTK3(Integer.valueOf((String) cbo7C_Nam.getSelectedItem()));
        }
    }//GEN-LAST:event_cbo7C_NamActionPerformed

    private void btn7C_XuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7C_XuatActionPerformed
        xuatTK3();
    }//GEN-LAST:event_btn7C_XuatActionPerformed

    private void cbo7D_NamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo7D_NamActionPerformed
        if (cbo7D_Nam.getItemCount() > 0) {
            taiListTK4(Integer.valueOf((String) cbo7D_Nam.getSelectedItem()));
        }
    }//GEN-LAST:event_cbo7D_NamActionPerformed

    private void btn7D_XuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7D_XuatActionPerformed
        xuatTK4();
    }//GEN-LAST:event_btn7D_XuatActionPerformed

    private void btn5A_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5A_XoaActionPerformed
        xoaHV_HV2();
    }//GEN-LAST:event_btn5A_XoaActionPerformed

    private void btn5A_CapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5A_CapNhatActionPerformed
        capNhatDiemHV();
    }//GEN-LAST:event_btn5A_CapNhatActionPerformed

    private void txt5A_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt5A_TimKiemKeyReleased
        timKiemHV_HV();
    }//GEN-LAST:event_txt5A_TimKiemKeyReleased

    private void tblQLHocVien_HocVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLHocVien_HocVienMouseClicked

    }//GEN-LAST:event_tblQLHocVien_HocVienMouseClicked

    private void btn5B_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5B_ThemActionPerformed
        themHV_NH2();
    }//GEN-LAST:event_btn5B_ThemActionPerformed

    private void txt5B_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt5B_TimKiemKeyReleased
        timKiemHV_NH();
    }//GEN-LAST:event_txt5B_TimKiemKeyReleased

    private void tblQLHocVien_NguoiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLHocVien_NguoiHocMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblQLHocVien_NguoiHocMouseClicked

    private void cboQLHocVien_ChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboQLHocVien_ChuyenDeActionPerformed
        if (cboQLHocVien_ChuyenDe.getItemCount() > 0) {
            List<Object[]> list = new ArrayList<>();
            cboQLHocVien_KhoaHoc.removeAllItems();

            list = ThongKeDAO.cboKhoaHoc((String) listTK2_CD.get(cboQLHocVien_ChuyenDe.getSelectedIndex())[1]);
            for (Object[] cbo : list) {
                cboQLHocVien_KhoaHoc.addItem((String) cbo[0]);
            }
        }
    }//GEN-LAST:event_cboQLHocVien_ChuyenDeActionPerformed

    private void cboQLHocVien_KhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboQLHocVien_KhoaHocActionPerformed
        if (cboQLHocVien_ChuyenDe.getItemCount() > 0) {
            taiListHocVien_HV((String) cboQLHocVien_KhoaHoc.getSelectedItem());
            taiListHocVien_NH((String) cboQLHocVien_KhoaHoc.getSelectedItem());
        }
    }//GEN-LAST:event_cboQLHocVien_KhoaHocActionPerformed

    private void cboQLHocVien_KhoaHocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboQLHocVien_KhoaHocItemStateChanged
        if (cboQLHocVien_ChuyenDe.getItemCount() > 0) {
            taiListHocVien_HV((String) cboQLHocVien_KhoaHoc.getSelectedItem());
            taiListHocVien_NH((String) cboQLHocVien_KhoaHoc.getSelectedItem());
        }
    }//GEN-LAST:event_cboQLHocVien_KhoaHocItemStateChanged

    private void lbl2A_AnhDaiDienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl2A_AnhDaiDienMouseClicked
        chonHinhChuyenDe();
    }//GEN-LAST:event_lbl2A_AnhDaiDienMouseClicked

    private void btn2A_TruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2A_TruocActionPerformed
        Truoc();
        txt2A_MaChuyenDe.setEnabled(false);
    }//GEN-LAST:event_btn2A_TruocActionPerformed

    private void btn2A_VeTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2A_VeTruocActionPerformed
        veTruoc();
        txt2A_MaChuyenDe.setEnabled(false);
    }//GEN-LAST:event_btn2A_VeTruocActionPerformed

    private void btn2A_VeSauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2A_VeSauActionPerformed
        veSau();
        txt2A_MaChuyenDe.setEnabled(false);
    }//GEN-LAST:event_btn2A_VeSauActionPerformed

    private void btn2A_SauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2A_SauActionPerformed
        Sau();
        txt2A_MaChuyenDe.setEnabled(false);
    }//GEN-LAST:event_btn2A_SauActionPerformed

    private void btn2A_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2A_ThemActionPerformed
        themChuyenDe();
    }//GEN-LAST:event_btn2A_ThemActionPerformed

    private void btn2A_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2A_SuaActionPerformed
        suaChuyenDe();
    }//GEN-LAST:event_btn2A_SuaActionPerformed

    private void btn2A_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2A_XoaActionPerformed
        xoaChuyenDe();
    }//GEN-LAST:event_btn2A_XoaActionPerformed

    private void btn2A_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2A_LamMoiActionPerformed
        lamMoi_CapNhatChuyenDe();
    }//GEN-LAST:event_btn2A_LamMoiActionPerformed

    private void tblQLChuyenDeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLChuyenDeMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
            this.row = tblQLChuyenDe.getSelectedRow();
            editTblQlChuyenDe();
            txt2A_MaChuyenDe.setEnabled(false);
        }
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
            this.row = tblQLChuyenDe.getSelectedRow();
            editTblQlChuyenDe();
            txt2A_MaChuyenDe.setEnabled(false);
            lblQLChuyenDe_CapNhatMouseClicked(evt);
        }
        if (evt.getButton() == MouseEvent.BUTTON3) {
            tblQLChuyenDe.clearSelection();
            row = -1;
            txt2A_MaChuyenDe.setEnabled(true);
        }
    }//GEN-LAST:event_tblQLChuyenDeMouseClicked

    private void txt2B_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt2B_TimKiemKeyReleased
        timKiemChuyenDe();
    }//GEN-LAST:event_txt2B_TimKiemKeyReleased

    private void txt3A_MaChuyenDeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt3A_MaChuyenDeKeyReleased
        keyMaChuyenDe_KhoaHoc();
    }//GEN-LAST:event_txt3A_MaChuyenDeKeyReleased

    private void cbo3A_TenChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo3A_TenChuyenDeActionPerformed
        keyTenCD_KhoaHoc();
    }//GEN-LAST:event_cbo3A_TenChuyenDeActionPerformed

    private void btn3A_TruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3A_TruocActionPerformed
        TruocKH();
        txt3A_MaKhoaHoc.setEnabled(false);
    }//GEN-LAST:event_btn3A_TruocActionPerformed

    private void btn3A_VeTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3A_VeTruocActionPerformed
        veTruocKH();
        txt3A_MaKhoaHoc.setEnabled(false);
    }//GEN-LAST:event_btn3A_VeTruocActionPerformed

    private void btn3A_VeSauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3A_VeSauActionPerformed
        veSauKH();
        txt3A_MaKhoaHoc.setEnabled(false);
    }//GEN-LAST:event_btn3A_VeSauActionPerformed

    private void btn3A_SauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3A_SauActionPerformed
        SauKH();
        txt3A_MaKhoaHoc.setEnabled(false);
    }//GEN-LAST:event_btn3A_SauActionPerformed

    private void btn3A_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3A_ThemActionPerformed
        themKhoaHoc();
    }//GEN-LAST:event_btn3A_ThemActionPerformed

    private void btn3A_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3A_SuaActionPerformed
        suaKhoaHoc();
    }//GEN-LAST:event_btn3A_SuaActionPerformed

    private void btn3A_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3A_XoaActionPerformed
        xoaKhoaHoc();
    }//GEN-LAST:event_btn3A_XoaActionPerformed

    private void btn3A_NhapMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3A_NhapMoiActionPerformed
        lamMoi_CapNhatKhoaHoc();
    }//GEN-LAST:event_btn3A_NhapMoiActionPerformed

    private void txt3B_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt3B_TimKiemKeyReleased
        timKiemKhoaHoc();
    }//GEN-LAST:event_txt3B_TimKiemKeyReleased

    private void tblQLKhoaHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLKhoaHocMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
            this.row = tblQLKhoaHoc.getSelectedRow();
            editTblQlKhoaHoc();
            txt3A_MaKhoaHoc.setEnabled(false);
        }
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
            this.row = tblQLKhoaHoc.getSelectedRow();
            editTblQlKhoaHoc();
            txt3A_MaKhoaHoc.setEnabled(false);
            lblQLKhoaHoc_CapNhatMouseClicked(evt);
        }
        if (evt.getButton() == MouseEvent.BUTTON3) {
            tblQLKhoaHoc.clearSelection();
            row = -1;
            txt3A_MaKhoaHoc.setEnabled(true);
        }
    }//GEN-LAST:event_tblQLKhoaHocMouseClicked

    private void lbl4A_AnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl4A_AnhMouseClicked
        chonHinh_NH();
    }//GEN-LAST:event_lbl4A_AnhMouseClicked

    private void btn4A_TruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4A_TruocActionPerformed
        first_NH();
        txt4A_MaNguoiHoc.setEnabled(false);
    }//GEN-LAST:event_btn4A_TruocActionPerformed

    private void btn4A_VeTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4A_VeTruocActionPerformed
        prev_NH();
        txt4A_MaNguoiHoc.setEnabled(false);
    }//GEN-LAST:event_btn4A_VeTruocActionPerformed

    private void btn4A_VeSauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4A_VeSauActionPerformed
        next_NH();
        txt4A_MaNguoiHoc.setEnabled(false);
    }//GEN-LAST:event_btn4A_VeSauActionPerformed

    private void btn4A_SauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4A_SauActionPerformed
        last_NH();
        txt4A_MaNguoiHoc.setEnabled(false);
    }//GEN-LAST:event_btn4A_SauActionPerformed

    private void btn4A_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4A_ThemActionPerformed
        Them_NH();
    }//GEN-LAST:event_btn4A_ThemActionPerformed

    private void btn4A_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4A_SuaActionPerformed
        Sua_NH();
    }//GEN-LAST:event_btn4A_SuaActionPerformed

    private void btn4A_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4A_XoaActionPerformed
        Xoa_NH();
    }//GEN-LAST:event_btn4A_XoaActionPerformed

    private void btn4A_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4A_LamMoiActionPerformed
        Moi_NH();
    }//GEN-LAST:event_btn4A_LamMoiActionPerformed

    private void txt4B_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt4B_TimKiemKeyReleased
        Tim_NH();
    }//GEN-LAST:event_txt4B_TimKiemKeyReleased

    private void tblQLNguoiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLNguoiHocMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
            index = tblQLNguoiHoc.getSelectedRow();
            edit_NH();
            txt4A_MaNguoiHoc.setEnabled(false);
        }
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
            index = tblQLNguoiHoc.getSelectedRow();
            edit_NH();
            txt4A_MaNguoiHoc.setEnabled(false);
            lblQLNguoiHoc_CapNhatMouseClicked(evt);
        }
        if (evt.getButton() == MouseEvent.BUTTON3) {
            tblQLNguoiHoc.clearSelection();
            index = -1;
            txt4A_MaNguoiHoc.setEnabled(true);
        }
    }//GEN-LAST:event_tblQLNguoiHocMouseClicked

    private void lbl6A_AnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl6A_AnhMouseClicked
        hienThiAnh();
    }//GEN-LAST:event_lbl6A_AnhMouseClicked

    private void lbl6A_HienMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl6A_HienMKMouseClicked
        hienMatKhau(lbl6A_HienMK, pwd6A_MatKhau);
    }//GEN-LAST:event_lbl6A_HienMKMouseClicked

    private void btn6A_TruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6A_TruocActionPerformed
        TruocNV();
        txt6A_MaNhanVien.setEnabled(false);
    }//GEN-LAST:event_btn6A_TruocActionPerformed

    private void btn6A_VeTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6A_VeTruocActionPerformed
        veTruocNV();
        txt6A_MaNhanVien.setEnabled(false);
    }//GEN-LAST:event_btn6A_VeTruocActionPerformed

    private void btn6A_VeSauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6A_VeSauActionPerformed
        veSauNV();
        txt6A_MaNhanVien.setEnabled(false);
    }//GEN-LAST:event_btn6A_VeSauActionPerformed

    private void btn6A_SauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6A_SauActionPerformed
        SauNV();
        txt6A_MaNhanVien.setEnabled(false);
    }//GEN-LAST:event_btn6A_SauActionPerformed

    private void btn6A_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6A_ThemActionPerformed
        themNhanVien();
    }//GEN-LAST:event_btn6A_ThemActionPerformed

    private void btn6A_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6A_SuaActionPerformed
        suaNhanVien();
    }//GEN-LAST:event_btn6A_SuaActionPerformed

    private void btn6A_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6A_XoaActionPerformed
        xoaNhanVien();
    }//GEN-LAST:event_btn6A_XoaActionPerformed

    private void btn6A_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6A_LamMoiActionPerformed
        clearNhanVien();
    }//GEN-LAST:event_btn6A_LamMoiActionPerformed

    private void txt6B_TimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt6B_TimKiemKeyReleased
        timKiemNhanVien();
    }//GEN-LAST:event_txt6B_TimKiemKeyReleased

    private void tblQLNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLNhanVienMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
            writeForm(listNhanVien.get(tblQLNhanVien.getSelectedRow()));
            row = tblQLNhanVien.getSelectedRow();
            txt6A_MaNhanVien.setEnabled(false);
        }
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
            writeForm(listNhanVien.get(tblQLNhanVien.getSelectedRow()));
            row = tblQLNhanVien.getSelectedRow();
            txt6A_MaNhanVien.setEnabled(false);
            lblQLNhanVien_CapNhatMouseClicked(evt);
        }
        if (evt.getButton() == MouseEvent.BUTTON3) {
            tblQLNhanVien.clearSelection();
            row = -1;
            txt6A_MaNhanVien.setEnabled(true);
        }
    }//GEN-LAST:event_tblQLNhanVienMouseClicked

    private void btnG1_ChonAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG1_ChonAnhActionPerformed
        chonFileBaoCao();
    }//GEN-LAST:event_btnG1_ChonAnhActionPerformed

    private void btnG1_GuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnG1_GuiActionPerformed
        guiBaoCao();
    }//GEN-LAST:event_btnG1_GuiActionPerformed

    private void btnE1_DangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE1_DangNhapActionPerformed
        huongDan("video/DangNhap.mp4");
    }//GEN-LAST:event_btnE1_DangNhapActionPerformed

    private void btnE1_QuenMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE1_QuenMatKhauActionPerformed
        huongDan("video/QuenMatKhau.mp4");
    }//GEN-LAST:event_btnE1_QuenMatKhauActionPerformed

    private void btnE1_DoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE1_DoiMatKhauActionPerformed
        huongDan("video/DoiMatKhau.mp4");
    }//GEN-LAST:event_btnE1_DoiMatKhauActionPerformed

    private void btnE1_NhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE1_NhanVienActionPerformed
        MessageHelper.alert(this, "ĐANG CẬP NHẬP...", "THÔNG BÁO", linkAnhDung);
    }//GEN-LAST:event_btnE1_NhanVienActionPerformed

    private void btnE1_ChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE1_ChuyenDeActionPerformed
        huongDan("video/ChuyenDe.mp4");
    }//GEN-LAST:event_btnE1_ChuyenDeActionPerformed

    private void btnE1_KhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE1_KhoaHocActionPerformed
        MessageHelper.alert(this, "ĐANG CẬP NHẬP...", "THÔNG BÁO", linkAnhDung);
    }//GEN-LAST:event_btnE1_KhoaHocActionPerformed

    private void btnE1_NguoiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE1_NguoiHocActionPerformed
        MessageHelper.alert(this, "ĐANG CẬP NHẬP...", "THÔNG BÁO", linkAnhDung);
    }//GEN-LAST:event_btnE1_NguoiHocActionPerformed

    private void btnE1_HocVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE1_HocVienActionPerformed
        MessageHelper.alert(this, "ĐANG CẬP NHẬP...", "THÔNG BÁO", linkAnhDung);
    }//GEN-LAST:event_btnE1_HocVienActionPerformed

    private void btnE1_ThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnE1_ThongKeActionPerformed
        huongDan("video/ThongKe.mp4");
    }//GEN-LAST:event_btnE1_ThongKeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            main batDau = new main();
            batDau.hienThiDialog(batDau.JDialog_ChaoMung);
            batDau.chaoMung();
            batDau.taiListAll();
        } catch (Exception e) {
            System.out.println("loi chao mung: " + e);
        }

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog JDialog_ChaoMung;
    private javax.swing.JDialog JDialog_GioiThieu;
    private javax.swing.JFrame JFrame_BaoCao;
    private javax.swing.JFrame JFrame_DangNhap;
    private javax.swing.JFrame JFrame_DoiMatKhau;
    private javax.swing.JFrame JFrame_HuongDan;
    private javax.swing.JFrame JFrame_QuenMatKhau;
    private javax.swing.JButton btn2A_LamMoi;
    private javax.swing.JButton btn2A_Sau;
    private javax.swing.JButton btn2A_Sua;
    private javax.swing.JButton btn2A_Them;
    private javax.swing.JButton btn2A_Truoc;
    private javax.swing.JButton btn2A_VeSau;
    private javax.swing.JButton btn2A_VeTruoc;
    private javax.swing.JButton btn2A_Xoa;
    private javax.swing.JButton btn3A_NhapMoi;
    private javax.swing.JButton btn3A_Sau;
    private javax.swing.JButton btn3A_Sua;
    private javax.swing.JButton btn3A_Them;
    private javax.swing.JButton btn3A_Truoc;
    private javax.swing.JButton btn3A_VeSau;
    private javax.swing.JButton btn3A_VeTruoc;
    private javax.swing.JButton btn3A_Xoa;
    private javax.swing.JButton btn4A_LamMoi;
    private javax.swing.JButton btn4A_Sau;
    private javax.swing.JButton btn4A_Sua;
    private javax.swing.JButton btn4A_Them;
    private javax.swing.JButton btn4A_Truoc;
    private javax.swing.JButton btn4A_VeSau;
    private javax.swing.JButton btn4A_VeTruoc;
    private javax.swing.JButton btn4A_Xoa;
    private javax.swing.JButton btn5A_CapNhat;
    private javax.swing.JButton btn5A_Xoa;
    private javax.swing.JButton btn5B_Them;
    private javax.swing.JButton btn6A_LamMoi;
    private javax.swing.JButton btn6A_Sau;
    private javax.swing.JButton btn6A_Sua;
    private javax.swing.JButton btn6A_Them;
    private javax.swing.JButton btn6A_Truoc;
    private javax.swing.JButton btn6A_VeSau;
    private javax.swing.JButton btn6A_VeTruoc;
    private javax.swing.JButton btn6A_Xoa;
    private javax.swing.JButton btn7A_Xuat;
    private javax.swing.JButton btn7B_Xuat;
    private javax.swing.JButton btn7C_Xuat;
    private javax.swing.JButton btn7D_Xuat;
    private javax.swing.JButton btnB1;
    private javax.swing.JButton btnC1;
    private javax.swing.JButton btnC1_GuiMaXacNhan;
    private javax.swing.JButton btnD1;
    private javax.swing.JButton btnE1_ChuyenDe;
    private javax.swing.JButton btnE1_DangNhap;
    private javax.swing.JButton btnE1_DoiMatKhau;
    private javax.swing.JButton btnE1_HocVien;
    private javax.swing.JButton btnE1_KhoaHoc;
    private javax.swing.JButton btnE1_NguoiHoc;
    private javax.swing.JButton btnE1_NhanVien;
    private javax.swing.JButton btnE1_QuenMatKhau;
    private javax.swing.JButton btnE1_ThongKe;
    private javax.swing.JButton btnG1_ChonAnh;
    private javax.swing.JButton btnG1_Gui;
    private javax.swing.JComboBox<String> cbo3A_TenChuyenDe;
    private javax.swing.JComboBox<String> cbo4A_GioiTinh;
    private javax.swing.JComboBox<String> cbo6A_ChucVu;
    private javax.swing.JComboBox<String> cbo6A_GioiTinh;
    private javax.swing.JComboBox<String> cbo7A_ThoiGian;
    private javax.swing.JComboBox<String> cbo7B_ChuyenDe;
    private javax.swing.JComboBox<String> cbo7B_KhoaHoc;
    private javax.swing.JComboBox<String> cbo7C_Nam;
    private javax.swing.JComboBox<String> cbo7D_Nam;
    private javax.swing.JComboBox<String> cboQLHocVien_ChuyenDe;
    private javax.swing.JComboBox<String> cboQLHocVien_KhoaHoc;
    private javax.swing.JCheckBox chkB1;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl1So;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl2A_AnhDaiDien;
    private javax.swing.JLabel lbl2A_HocPhi;
    private javax.swing.JLabel lbl2A_MaChuyenDe;
    private javax.swing.JLabel lbl2A_MoTa;
    private javax.swing.JLabel lbl2A_TenChuyenDe;
    private javax.swing.JLabel lbl2A_ThoiLuong;
    private javax.swing.JLabel lbl2B_TimKiem;
    private javax.swing.JLabel lbl2So;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl3A_GhiChu;
    private javax.swing.JLabel lbl3A_HocPhi;
    private javax.swing.JLabel lbl3A_MaChuyenDe;
    private javax.swing.JLabel lbl3A_MaKhoaHoc;
    private javax.swing.JLabel lbl3A_NgayKhaiGiang;
    private javax.swing.JLabel lbl3A_NgayTao;
    private javax.swing.JLabel lbl3A_TenChuyenDe;
    private javax.swing.JLabel lbl3A_ThoiLuong;
    private javax.swing.JLabel lbl3B_TimKiem;
    private javax.swing.JLabel lbl3So;
    private javax.swing.JLabel lbl4A_Anh;
    private javax.swing.JLabel lbl4A_DiaChi;
    private javax.swing.JLabel lbl4A_Email;
    private javax.swing.JLabel lbl4A_GhiChu;
    private javax.swing.JLabel lbl4A_GioiTinh;
    private javax.swing.JLabel lbl4A_HoTen;
    private javax.swing.JLabel lbl4A_MaNguoiHoc;
    private javax.swing.JLabel lbl4A_NgayDangKy;
    private javax.swing.JLabel lbl4A_NgaySinh;
    private javax.swing.JLabel lbl4A_SoDienThoai;
    private javax.swing.JLabel lbl4B_TimKiem;
    private javax.swing.JLabel lbl5A_TimKiem;
    private javax.swing.JLabel lbl5B_TimKiem;
    private javax.swing.JLabel lbl6A_Anh;
    private javax.swing.JLabel lbl6A_CCCD;
    private javax.swing.JLabel lbl6A_ChucVu;
    private javax.swing.JLabel lbl6A_DiaChi;
    private javax.swing.JLabel lbl6A_Email;
    private javax.swing.JLabel lbl6A_GioiTinh;
    private javax.swing.JLabel lbl6A_HienMK;
    private javax.swing.JLabel lbl6A_HoTen;
    private javax.swing.JLabel lbl6A_MaNhanVien;
    private javax.swing.JLabel lbl6A_MatKhau;
    private javax.swing.JLabel lbl6A_NgaySinh;
    private javax.swing.JLabel lbl6A_SoDienThoai;
    private javax.swing.JLabel lbl6B_TimKiem;
    private javax.swing.JLabel lbl7A_ThoiGian;
    private javax.swing.JLabel lbl7B_ChuyenDe;
    private javax.swing.JLabel lbl7B_KhoaHoc;
    private javax.swing.JLabel lbl7C_Nam;
    private javax.swing.JLabel lbl7D_Nam;
    private javax.swing.JLabel lblA1;
    private javax.swing.JLabel lblAnhDaiDien;
    private javax.swing.JLabel lblAnhNen;
    private javax.swing.JLabel lblB1;
    private javax.swing.JLabel lblB1_HienMK;
    private javax.swing.JLabel lblB1_QuenMatKhau;
    private javax.swing.JLabel lblBaoCao;
    private javax.swing.JLabel lblC1;
    private javax.swing.JLabel lblC1_ClearQMK;
    private javax.swing.JLabel lblC1_DangGui;
    private javax.swing.JLabel lblC1_HienMKM;
    private javax.swing.JLabel lblC1_HienXN;
    private javax.swing.JLabel lblC1_QuayLai;
    private javax.swing.JLabel lblD1;
    private javax.swing.JLabel lblD1_HienMKHT;
    private javax.swing.JLabel lblD1_HienMKM;
    private javax.swing.JLabel lblD1_HienXN;
    private javax.swing.JLabel lblDangXuat;
    private javax.swing.JLabel lblE1;
    private javax.swing.JLabel lblF1;
    private javax.swing.JLabel lblF1_DiaChi;
    private javax.swing.JLabel lblF1_LienHe;
    private javax.swing.JLabel lblF1_p1;
    private javax.swing.JLabel lblF1_p2;
    private javax.swing.JLabel lblG1_BaoCao;
    private javax.swing.JLabel lblG1_NoiDung;
    private javax.swing.JLabel lblG1_TieuDe;
    private javax.swing.JLabel lblMaNhanVien;
    private javax.swing.JLabel lblMore;
    private javax.swing.JLabel lblP1;
    private javax.swing.JLabel lblP2;
    private javax.swing.JLabel lblP3;
    private javax.swing.JLabel lblQLChuyenDe;
    private javax.swing.JLabel lblQLChuyenDe_CapNhat;
    private javax.swing.JLabel lblQLChuyenDe_DanhSach;
    private javax.swing.JLabel lblQLChuyenDe_Title;
    private javax.swing.JLabel lblQLHocVien;
    private javax.swing.JLabel lblQLHocVien_ChuyenDe;
    private javax.swing.JLabel lblQLHocVien_HocVien;
    private javax.swing.JLabel lblQLHocVien_KhoaHoc;
    private javax.swing.JLabel lblQLHocVien_NguoiHoc;
    private javax.swing.JLabel lblQLHocVien_Title;
    private javax.swing.JLabel lblQLKhoaHoc;
    private javax.swing.JLabel lblQLKhoaHoc_CapNhat;
    private javax.swing.JLabel lblQLKhoaHoc_DanhSach;
    private javax.swing.JLabel lblQLKhoaHoc_Title;
    private javax.swing.JLabel lblQLNguoiHoc;
    private javax.swing.JLabel lblQLNguoiHoc_CapNhat;
    private javax.swing.JLabel lblQLNguoiHoc_DanhSach;
    private javax.swing.JLabel lblQLNguoiHoc_Title;
    private javax.swing.JLabel lblQLNhanVien;
    private javax.swing.JLabel lblQLNhanVien_CapNhat;
    private javax.swing.JLabel lblQLNhanVien_DanhSach;
    private javax.swing.JLabel lblQLNhanVien_Title;
    private javax.swing.JLabel lblTK1;
    private javax.swing.JLabel lblTK2;
    private javax.swing.JLabel lblTK3;
    private javax.swing.JLabel lblTK4;
    private javax.swing.JLabel lblTKTongHop;
    private javax.swing.JLabel lblTKTongHop_Title;
    private javax.swing.JLabel lblTrangChu;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JMenuItem mniBaoCao;
    private javax.swing.JMenuItem mniDangXuat;
    private javax.swing.JMenuItem mniDoiMatKhau;
    private javax.swing.JMenuItem mniGioiThieu;
    private javax.swing.JMenuItem mniHuongDan;
    private javax.swing.JMenuItem mniNhVien;
    private javax.swing.JMenuItem mniQLChuyenDe;
    private javax.swing.JMenuItem mniQLHoVien;
    private javax.swing.JMenuItem mniQLKhHoc;
    private javax.swing.JMenuItem mniQLNgHoc;
    private javax.swing.JMenuItem mniTK1;
    private javax.swing.JMenuItem mniTK2;
    private javax.swing.JMenuItem mniTK3;
    private javax.swing.JMenuItem mniTK4;
    private javax.swing.JMenuItem mniThoat;
    private javax.swing.JMenu mnuHeThong;
    private javax.swing.JMenu mnuQuanLy;
    private javax.swing.JMenu mnuThongKe;
    private javax.swing.JMenu mnuTroGiup;
    private javax.swing.JMenuBar mnubar;
    private javax.swing.JPanel pnl6A_MatKhau;
    private javax.swing.JPanel pnlA1;
    private javax.swing.JPanel pnlA2;
    private javax.swing.JPanel pnlAnhDaiDien;
    private javax.swing.JPanel pnlB1;
    private javax.swing.JPanel pnlC1;
    private javax.swing.JPanel pnlD1;
    private javax.swing.JPanel pnlDieuHuong;
    private javax.swing.JPanel pnlE0;
    private javax.swing.JPanel pnlE1;
    private javax.swing.JPanel pnlE1_video;
    private javax.swing.JPanel pnlF1;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlNoiDung;
    private javax.swing.JPanel pnlNoiDung_0;
    private javax.swing.JPanel pnlQLChuyenDe;
    private javax.swing.JPanel pnlQLChuyenDe_2;
    private javax.swing.JPanel pnlQLChuyenDe_Body;
    private javax.swing.JPanel pnlQLChuyenDe_CapNhat;
    private javax.swing.JPanel pnlQLChuyenDe_DanhSach;
    private javax.swing.JPanel pnlQLChuyenDe_Head;
    private javax.swing.JPanel pnlQLHocVien;
    private javax.swing.JPanel pnlQLHocVien_5;
    private javax.swing.JPanel pnlQLHocVien_Body;
    private javax.swing.JPanel pnlQLHocVien_Head;
    private javax.swing.JPanel pnlQLHocVien_HocVien;
    private javax.swing.JPanel pnlQLHocVien_NguoiHoc;
    private javax.swing.JPanel pnlQLKhoaHoc;
    private javax.swing.JPanel pnlQLKhoaHoc_3;
    private javax.swing.JPanel pnlQLKhoaHoc_Body;
    private javax.swing.JPanel pnlQLKhoaHoc_CapNhat;
    private javax.swing.JPanel pnlQLKhoaHoc_DanhSach;
    private javax.swing.JPanel pnlQLKhoaHoc_Head;
    private javax.swing.JPanel pnlQLNguoiHoc;
    private javax.swing.JPanel pnlQLNguoiHoc_4;
    private javax.swing.JPanel pnlQLNguoiHoc_Body;
    private javax.swing.JPanel pnlQLNguoiHoc_CapNhat;
    private javax.swing.JPanel pnlQLNguoiHoc_DanhSach;
    private javax.swing.JPanel pnlQLNguoiHoc_Head;
    private javax.swing.JPanel pnlQLNhanVien;
    private javax.swing.JPanel pnlQLNhanVien_6;
    private javax.swing.JPanel pnlQLNhanVien_Body;
    private javax.swing.JPanel pnlQLNhanVien_CapNhat;
    private javax.swing.JPanel pnlQLNhanVien_DanhSach;
    private javax.swing.JPanel pnlQLNhanVien_Head;
    private javax.swing.JPanel pnlTK1;
    private javax.swing.JPanel pnlTK2;
    private javax.swing.JPanel pnlTK3;
    private javax.swing.JPanel pnlTK4;
    private javax.swing.JPanel pnlTKTongHop;
    private javax.swing.JPanel pnlTKTongHop_7;
    private javax.swing.JPanel pnlTKTongHop_Body;
    private javax.swing.JPanel pnlTKTongHop_Head;
    private javax.swing.JPanel pnlTrangChu;
    private javax.swing.JPanel pnlTrangThai;
    private javax.swing.JProgressBar prgA1;
    private javax.swing.JPasswordField pwd6A_MatKhau;
    private javax.swing.JPasswordField pwdB1_MatKhau;
    private javax.swing.JPasswordField pwdC1_MatKhauMoi;
    private javax.swing.JPasswordField pwdC1_XacNhan;
    private javax.swing.JPasswordField pwdD1_MatKhauHienTai;
    private javax.swing.JPasswordField pwdD1_MatKhauMoi;
    private javax.swing.JPasswordField pwdD1_XacNhan;
    private javax.swing.JScrollPane scr2A;
    private javax.swing.JScrollPane scr2B;
    private javax.swing.JScrollPane scr3A;
    private javax.swing.JScrollPane scr3B;
    private javax.swing.JScrollPane scr4A;
    private javax.swing.JScrollPane scr4B;
    private javax.swing.JScrollPane scr5A;
    private javax.swing.JScrollPane scr5B;
    private javax.swing.JScrollPane scr7C;
    private javax.swing.JScrollPane scr7D;
    private javax.swing.JPopupMenu.Separator sprA1;
    private javax.swing.JPopupMenu.Separator sprB1;
    private javax.swing.JPopupMenu.Separator sprB2;
    private javax.swing.JScrollPane src6B;
    private javax.swing.JScrollPane src7A;
    private javax.swing.JScrollPane src7B;
    private javax.swing.JScrollPane srcG1;
    private javax.swing.JTable tblQLChuyenDe;
    private javax.swing.JTable tblQLHocVien_HocVien;
    private javax.swing.JTable tblQLHocVien_NguoiHoc;
    private javax.swing.JTable tblQLKhoaHoc;
    private javax.swing.JTable tblQLNguoiHoc;
    private javax.swing.JTable tblQLNhanVien;
    private javax.swing.JTable tblTKDiem_ChuyenDe;
    private javax.swing.JTable tblTKDiem_KhoaHoc;
    private javax.swing.JTable tblTKDoanhThu_ChuyenDe;
    private javax.swing.JTable tblTKNguoiHoc;
    private javax.swing.JTextField txt2A_HocPhi;
    private javax.swing.JTextField txt2A_MaChuyenDe;
    private javax.swing.JTextArea txt2A_MoTa;
    private javax.swing.JTextField txt2A_TenChuyenDe;
    private javax.swing.JTextField txt2A_ThoiLuong;
    private javax.swing.JTextField txt2B_TimKiem;
    private javax.swing.JTextArea txt3A_GhiChu;
    private javax.swing.JTextField txt3A_HocPhi;
    private javax.swing.JTextField txt3A_MaChuyenDe;
    private javax.swing.JTextField txt3A_MaKhoaHoc;
    private javax.swing.JTextField txt3A_NgayKhaiGiang;
    private javax.swing.JTextField txt3A_NgayTao;
    private javax.swing.JTextField txt3A_ThoiLuong;
    private javax.swing.JTextField txt3B_TimKiem;
    private javax.swing.JTextField txt4A_DiaChi;
    private javax.swing.JTextField txt4A_Email;
    private javax.swing.JTextArea txt4A_GhiChu;
    private javax.swing.JTextField txt4A_HoTen;
    private javax.swing.JTextField txt4A_MaNguoiHoc;
    private javax.swing.JTextField txt4A_NgayDangKy;
    private javax.swing.JTextField txt4A_NgaySinh;
    private javax.swing.JTextField txt4A_SoDienThoai;
    private javax.swing.JTextField txt4B_TimKiem;
    private javax.swing.JTextField txt5A_TimKiem;
    private javax.swing.JTextField txt5B_TimKiem;
    private javax.swing.JTextField txt6A_CCCD;
    private javax.swing.JTextField txt6A_DiaChi;
    private javax.swing.JTextField txt6A_Email;
    private javax.swing.JTextField txt6A_HoTen;
    private javax.swing.JTextField txt6A_MaNhanVien;
    private javax.swing.JTextField txt6A_NgaySinh;
    private javax.swing.JTextField txt6A_SoDienThoai;
    private javax.swing.JTextField txt6B_TimKiem;
    private javax.swing.JTextField txtB1_TaiKhoan;
    private javax.swing.JTextField txtC1_Email;
    private javax.swing.JTextField txtC1_MaXacNhan;
    private javax.swing.JTextField txtC1_TaiKhoan;
    private javax.swing.JTextArea txtG1_NoiDung;
    private javax.swing.JTextField txtG1_TieuDe;
    // End of variables declaration//GEN-END:variables

    // Video hướng dẫn
    private final JFXPanel jfxpanel = new JFXPanel();

    private void huongDan(String linkVideo) {
        createScene(linkVideo);
        jfxpanel.setSize(1001, 530);
        pnlE1_video.add(jfxpanel);
    }

    private void createScene(String linkVideo) {
        int ww = pnlE1_video.getWidth();
        int hh = pnlE1_video.getHeight();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                File tep = new File(linkVideo);
                MediaPlayer mplayer = new MediaPlayer(new Media(tep.toURI().toString()));
                MediaView mv = new MediaView(mplayer);
                mv.setFitWidth(ww);

                jfxpanel.setScene(new Scene(new Group(mv)));
                mplayer.setVolume(0.7);
                mplayer.setCycleCount(MediaPlayer.INDEFINITE);
                mplayer.play();
            }
        });
    }
}
