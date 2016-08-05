
package pptik.startup.ghvmobile.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Model {

    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("program")
    @Expose
    public List<Program> materi = new ArrayList<Program>();

    @SerializedName("kuis")
    @Expose
    public List<Kuis> kuis = new ArrayList<Kuis>();
    @SerializedName("kelas")
    @Expose
    public List<Kelas> kelas = new ArrayList<Kelas>();

}
