package firstbelajar.digitalsoftware.tiketsaya;

public class MyTicket {
    String nama_wisata, lokasi, jumlah_tiket;

    public MyTicket(String nama_wisata, String lokasi, String jumlah_tiket) {
        this.nama_wisata = nama_wisata;
        this.lokasi = lokasi;
        this.jumlah_tiket = jumlah_tiket;
    }

    public MyTicket() {
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getJumlah_tiket() {
        return jumlah_tiket;
    }
}
