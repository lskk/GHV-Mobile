package pptik.startup.ghvmobile.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fiyyanp on 5/20/2016.
 */
public class Kelas {
    @SerializedName("id_kelas")
    @Expose
    public Integer idKelas;
    @SerializedName("id_pengawas")
    @Expose
    public Integer idPengawas;
    @SerializedName("id_siswa")
    @Expose
    public Integer idSiswa;
    @SerializedName("id_materi")
    @Expose
    public Integer idMateri;
    @SerializedName("nama_kelas")
    @Expose
    public String namaKelas;
    @SerializedName("kode")
    @Expose
    public String kode;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
}
