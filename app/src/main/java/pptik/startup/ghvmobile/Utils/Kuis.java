package pptik.startup.ghvmobile.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fiyyanp on 5/9/2016.
 */
public class Kuis {
    @SerializedName("id_quiz")
    @Expose
    public Integer idQuiz;
    @SerializedName("id_materi")
    @Expose
    public Integer idMateri;
    @SerializedName("no_quiz")
    @Expose
    public Integer noQuiz;
    @SerializedName("isi_quiz")
    @Expose
    public String isiQuiz;
    @SerializedName("jawaban1")
    @Expose
    public String jawaban1;
    @SerializedName("jawaban2")
    @Expose
    public String jawaban2;
    @SerializedName("jawaban3")
    @Expose
    public String jawaban3;
    @SerializedName("jawaban4")
    @Expose
    public String jawaban4;
    @SerializedName("jawaban_benar")
    @Expose
    public Integer jawabanBenar;
    @SerializedName("poin")
    @Expose
    public Integer poin;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
}
