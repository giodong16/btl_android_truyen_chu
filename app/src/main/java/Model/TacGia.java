package Model;

import java.util.ArrayList;
import java.util.List;

public class TacGia {
        private String maTacGia;
        private String tenTacGia;
        private List<Truyen> truyenList;
    public TacGia() {
    }
        public TacGia(String maTacGia, String tenTacGia) {
            this.maTacGia = maTacGia;
            this.tenTacGia = tenTacGia;
            this.truyenList = new ArrayList<>();
        }

    public TacGia(String maTacGia, String tenTacGia, List<Truyen> truyenList) {
        this.maTacGia = maTacGia;
        this.tenTacGia = tenTacGia;
        this.truyenList = truyenList;
    }

    public String getMaTacGia() {
            return maTacGia;
        }

        public void setMaTacGia(String maTacGia) {
            this.maTacGia = maTacGia;
        }

        public String getTenTacGia() {
            return tenTacGia;
        }

        public void setTenTacGia(String tenTacGia) {
            this.tenTacGia = tenTacGia;
        }

        public List<Truyen> getTruyenList() {
            return truyenList;
        }

        public void setTruyenList(List<Truyen> truyenList) {
            this.truyenList = truyenList;
        }
    }