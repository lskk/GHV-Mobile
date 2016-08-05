package pptik.startup.ghvmobile.Support;
import java.io.Serializable;
/**
 * Created by GIGABYTE on 16/06/2016.
 */
public class DataProgram implements Serializable {
    private String _nama_program, _lokasi_program;
    private int _id_program;

    public String getNamaProgram() {
        return _nama_program;
    }
    public void setNamaProgram(String _nama_program) {
        this._nama_program = _nama_program;
    }

    public String getLokasiProgram() {
        return _lokasi_program;
    }
    public void setLokasiProgram(String _lokasi_program) {
        this._lokasi_program = _lokasi_program;
    }

    public int getIdProgram() {
        return _id_program;
    }
    public void setIdProgram(int _id_program) {
        this._id_program = _id_program;
    }
}
