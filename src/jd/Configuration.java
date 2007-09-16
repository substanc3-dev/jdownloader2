package jd;

import java.io.Serializable;
import java.util.Vector;

import jd.controlling.interaction.Interaction;
import jd.controlling.interaction.InteractionTrigger;
import jd.controlling.interaction.JAntiCaptcha;
import jd.controlling.interaction.WebUpdate;
import jd.router.RouterData;

/**
 * In dieser Klasse werden die benutzerspezifischen Einstellungen festgehalten
 * 
 * @author astaldo
 */

public class Configuration extends Property implements Serializable {
    /**
     * Gibt an ob die SerializeFunktionen im XMl MOdus Arbeiten oder nocht
     */
    public transient static boolean saveAsXML             = false;

    /**
     * serialVersionUID
     */
    private static final long       serialVersionUID      = -2709887320616014389L;

    private boolean                 useJAC                = true;

    /**
     * Hier wird das Downloadverzeichnis gespeichert
     */
    private String                  downloadDirectory     = ".";

    /**
     * Die unterschiedlichen Interaktionen. (ZB Reconnect nach einem Download)
     */

    private Vector<Interaction>     interactions          = new Vector<Interaction>();

    /**
     * Hier sind die Angaben für den Router gespeichert
     */

    private RouterData              routerData            = new RouterData();

    /**
     * Benutzername für den Router
     */

    private String                  routerUsername        = null;

    /**
     * Gibt an wie oft Versucht werden soll eine neue IP zu bekommen. (1&1 lässt
     * grüßen)
     */
    private int                     reconnectRetries      = 0;

    /**
     * Password für den Router
     */
    private String                  routerPassword        = null;

    /**
     * Fertige Downloads entfernen
     */
    private boolean                 removeDownloadedFiles = true;

    /**
     * Level für das Logging
     */
    private String                  loggerLevel           = "ALL";

    /**
     * Wartezeit zwischen reconnect und erstem IP Check
     */
    private int                     waitForIPCheck        = 0;

    private String                  version="";
    /**
     * Download timeout);
     */
 private int readTimeout=10000;
 /**
  * Download timeout);
  */
 private int connectTimeout=10000;
    /**
     * Konstruktor für ein Configuration Object
     */
    public Configuration() {
    // WebUpdate updater=new WebUpdate();
    // updater.setTrigger(Interaction.INTERACTION_APPSTART);
    // interactions.add(updater);
    }

    /**
     * Gibt das gewählte downloadDirectory zurück
     * @return dlDir
     */
    public String getDownloadDirectory() {
        return downloadDirectory;
    }

    // public HashMap<Integer, Vector<Interaction>> getInteractionMap() { return
    // interactionMap; }
    /**
     * @return Gibt das Routeradmin Passwort zurück
     */
    public String getRouterPassword() {
        return routerPassword;
    }

    /**
     * @return gibt den router-admin-Username zurück
     */
    public String getRouterUsername() {
        return routerUsername;
    }

    /**
     * GIbt das routerdata objekt zurück. darin sind alle informationen gespeichert die aus der routerdata.xml importiert worden sind. (für einen router)
     * @return Gibt das routerdata objekt zurück
     */
    public RouterData getRouterData() {
        return routerData;
    }

    /**
     * Gibt an ob JAC verwendet werden soll
     * TODO: veraltet. nicht emhr verwenden!
     * @return jac oder nicht jac
     */
    public boolean useJAC() {
        return useJAC;
    }

    /**
     * @param downloadDirectory
     */
    public void setDownloadDirectory(String downloadDirectory) {
        this.downloadDirectory = downloadDirectory;
    }

    /**
     * @param routerPassword
     */
    public void setRouterPassword(String routerPassword) {
        this.routerPassword = routerPassword;
    }

    /**
     * @param routerUsername
     */
    public void setRouterUsername(String routerUsername) {
        this.routerUsername = routerUsername;
    }

    /**
     * @param routerData
     */
    public void setRouterData(RouterData routerData) {
        this.routerData = routerData;
    }

    /**
     * @param useJAC
     */
    public void setUseJAC(boolean useJAC) {
        this.useJAC = useJAC;
    }

    /**
     * @return GIbt zurück welches Loggerlevel eingestellt ist
     */
    public String getLoggerLevel() {
        return loggerLevel;
    }

    /** Setzt das Loggerlevel. Das Logegrlevel bestimmt bis zu welcher Wichtigkeit debug Informationen im Log ausgegeben werden
     * @param loggerLevel
     */
    public void setLoggerLevel(String loggerLevel) {
        this.loggerLevel = loggerLevel;
    }

    /**
     * @return the removeDownloadedFiles
     */
    public boolean isRemoveDownloadedFiles() {
        return removeDownloadedFiles;
    }

    /**
     * @param removeDownloadedFiles the removeDownloadedFiles to set
     */
    public void setRemoveDownloadedFiles(boolean removeDownloadedFiles) {
        this.removeDownloadedFiles = removeDownloadedFiles;
    }

    /**
     * @return the reconnectRetries
     */
    public int getReconnectRetries() {
        return reconnectRetries;
    }

    /**
     * @param reconnectRetries the reconnectRetries to set
     */
    public void setReconnectRetries(int reconnectRetries) {
        this.reconnectRetries = reconnectRetries;
    }

    /**
     * Wartezeit zwischen reconnect und erstem IP Check
     * 
     * @return Wartezeit zwischen reconnect und ip-check
     */
    public int getWaitForIPCheck() {
        return waitForIPCheck;
    }

    /**
     * Setztd ie Wartezeit zwischen dem Reconnect und dem ersten IP-Check
     * @param waitForIPCheck
     */
    public void setWaitForIPCheck(int waitForIPCheck) {
        this.waitForIPCheck = waitForIPCheck;
    }

    /**
     * Gibt die Interactionen zurück. Alle eingestellten INteractionen werden hier in einem vector zurückgegeben
     * 
     * @return  Vector<Interaction> 
     */

    public Vector<Interaction> getInteractions() {
        return interactions;
    }

    /**
     * Setzt die INteractionen
     * 
     * @param interactions
     */
    public void setInteractions(Vector<Interaction> interactions) {
        this.interactions = interactions;
    }

    /**
     * Gibt alle Interactionen zurück bei denen die TRigger übereinstimmen. z.B.
     * alle reconnect Aktionen
     * 
     * @param it
     * @return Alle interactionen mit dem TRigger it

     */
    public Vector<Interaction> getInteractions(InteractionTrigger it) {
        Vector<Interaction> ret = new Vector<Interaction>();
        for (int i = 0; i < interactions.size(); i++) {
            if (interactions.elementAt(i).getTrigger().getID() == it.getID()) ret.add(interactions.elementAt(i));
        }
        return ret;
    }

    /**
     * Gibt alle Interactionen zurück bei der die AKtion inter gleicht
     * 
     * @param inter
     * @return Alle interactionen mit dem Selben interaction-Event wie inter
     */
    public Vector<Interaction> getInteractions(Interaction inter) {
        Vector<Interaction> ret = new Vector<Interaction>();
        for (int i = 0; i < interactions.size(); i++) {

            if (inter.getInteractionName().equals(interactions.elementAt(i).getInteractionName())) ret.add(interactions.elementAt(i));
        }
        return ret;
    }

    /**
     * Setzt die Version der Configfile
     * 
     * @param version
     */
    public void setConfigurationVersion(String version) {
        this.version = version;
    }

    /**
     * Gibt die version der Configfile zurück. Ändert sich die Konfigversion, werden die defaulteinstellungen erneut geschrieben. So wird sichergestellt, dass bei einem Update eine Aktuelle Configfie erstellt wird
     * 
     * @return Versionsstring der Konfiguration
     */
    public String getConfigurationVersion() {
        if(version==null)return "0.0.0";
        return version;
    }

    /**
     * Legt die defaulteinstellungen in das configobjekt
     */
    public void setDefaultValues() {
        // Setze AutoUpdater

        WebUpdate wu = new WebUpdate();
        if (getInteractions(wu).size() == 0) {
            InteractionTrigger it = Interaction.INTERACTION_APPSTART;
            wu.setTrigger(it);
            interactions.add(wu);
        }
        
        JAntiCaptcha jac = new JAntiCaptcha();
        if (getInteractions(Interaction.INTERACTION_DOWNLOAD_CAPTCHA).size() == 0) {
            InteractionTrigger it = Interaction.INTERACTION_DOWNLOAD_CAPTCHA;
            jac.setTrigger(it);
            interactions.add(jac);
        }

        
        setConfigurationVersion(JDUtilities.JD_VERSION);
    }


/**
 * GIbt alle Properties der Config aus
 * @return toString
 */
public String toString(){
    return "Configuration "+this.getProperties()+" INteraction "+this.interactions;
}

/**
 * 
 * @return readTimeout in Millisekunden (für den eigentlichen download)
 */
public int getReadTimeout() {
    return readTimeout;
}

/**Setzt den lese-Timeout für den Download
 * @param readTimeout
 */
public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
}
/**
 * 
 * @return gibt den requestTimeout für den download an
 */
public int getConnectTimeout() {
    return connectTimeout;
}
/**
 * Setzt den requestTimeout für den Download
 * @param connectTimeout
 */
public void setConnectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
}
}
