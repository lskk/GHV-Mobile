package pptik.startup.ghvmobile.setup;


public interface ApplicationConstants {

    /*Server API Key help
    AIzaSyDFVG5x8zo1blEqSGq95fAt80-uOTuonPQ
    Sender ID help
    135626507167*/
    // Php Application URL to store Reg ID created
   // static final String APP_SERVER_URL = "http://gcm.hynra.com/insertuser.php";

    // Google Project Number
   static final String GOOGLE_PROJ_ID = "135626507167";
    // Message Key
  static final String MSG_KEY = "m";
  //  static final String MSG_KEY_USER_OJEK = "g";
    // http url
  //  static final String HTTP_URL = "http://svc-bsts.lskk.ee.itb.ac.id/dev/api/";
    static final String HTTP_URL = "http://167.205.7.228:8087/v1/";
    static final String API_REGISTRATION = "http://bsts-svc.lskk.ee.itb.ac.id/dev/api/opank/daftaropank/";
    static final String API_LOGIN = "http://167.205.7.228:8087/v1/useract/authenticate";
    static final String API_GET_START_PROGRAM = "http://167.205.7.228:8087/v1/beritaact/showstart";
    static final String API_GET_ALL_PROGRAM = "http://167.205.7.228:8087/v1/beritaact/showallverified";
    static final String API_DAFTAR_RELAWAN = "http://167.205.7.228:8087/v1/useract/storerelawan/";
    static final String API_GET_RELAWAN_BY_ID = "http://167.205.7.228:8087/v1/useract/profileshowbyid/";
    static final String API_GET_STATUS_DAFTAR = "http://167.205.7.228:8087/v1/useract/statusdaftar/";
    static final String API_GET_LIST_BELUM_DI_APPROVE = "http://167.205.7.228:8087/v1/useract/listbelumapprove";
    static final String API_GET_LIST_SUDAH_DI_APPROVE = "http://167.205.7.228:8087/v1/useract/listsudahapprove";
    static final String API_APPROVAL = "http://167.205.7.228:8087/v1/useract/approverelawan/";
    static final String API_GET_PROGRAM_SUDAH_DI_APPROVE = "http://167.205.7.228:8087/v1/beritaact/listsudahapprove";
    static final String API_GET_PROGRAM_BELUM_DI_APPROVE = "http://167.205.7.228:8087/v1/beritaact/listbelumapprove";
    static final String API_GET_PROGRAM_BY_ID = "http://167.205.7.228:8087/v1/beritaact/beritabyid/";
    static final String API_GET_PROGRAM_BY_ID_USER = "http://167.205.7.228:8087/v1/beritaact/beritabyiduser/";
    static final String API_APPROVAL_BERITA = "http://167.205.7.228:8087/v1/beritaact/approveberita/";
    static final String API_DELETE_BERITA = "http://167.205.7.228:8087/v1/beritaact/deleteberitabyid/";

  static final String FINE_REQUEST="FinERequest";
  static final String REG_ID = "regId";
  static final String EMAIL_ID = "eMailId";
  static final String LEVEL_ID = "roleId";
  static final String BSTS_ID = "userId";
  static final String USER_ID="UsErId";
  static final String USER_PREFS_NAME="UserDetails";

}