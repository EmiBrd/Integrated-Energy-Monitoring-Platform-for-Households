package ro.tuc.ds2020.serverPackage;

import java.util.Date;
import java.util.List;

public interface MeasureValue {

    Double[][] getHourlyEnergConsSubpunct1(Long idClient, Date dataAlegere, Integer ziuaD);
    Double[] getAverageEnergConsSubpunct2(Long idClient);

    List<String[]> sendDevicesToClient(Long idClient);
    Integer[] getBestTimeToStartSubpunct4(Long idClient, Integer nrOreProgram);

    String[] loginPentruGUIClient(String username, String password);

}
