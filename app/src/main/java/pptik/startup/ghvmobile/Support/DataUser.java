package pptik.startup.ghvmobile.Support;
import java.io.Serializable;
/**
 * Created by GIGABYTE on 16/06/2016.
 */
public class DataUser implements Serializable {
    private String _nama_lengkap, _email;
    private int _id_user;

    public String getNamaLengkap() {
        return _nama_lengkap;
    }
    public void setNamaLengkap(String _nama_lengkap) {
        this._nama_lengkap = _nama_lengkap;
    }

    public String getEmail() {
        return _email;
    }
    public void setEmail(String _email) {
        this._email = _email;
    }

    public int getIdUser() {
        return _id_user;
    }
    public void setIdUser(int _id_user) {
        this._id_user = _id_user;
    }
}
