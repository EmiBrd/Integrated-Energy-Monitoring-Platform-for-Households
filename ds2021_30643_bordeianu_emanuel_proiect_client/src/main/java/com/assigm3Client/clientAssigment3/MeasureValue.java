package com.assigm3Client.clientAssigment3;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MeasureValue {

    Double[][] getHourlyEnergConsSubpunct1(Long idClient, Date dataAlegere, Integer ziuaD);
    Double[] getAverageEnergConsSubpunct2(Long idClient);

    List<String[]> sendDevicesToClient(Long idClient);
    Integer[] getBestTimeToStartSubpunct4(Long idClient, Integer nrOreProgram);

    String[] loginPentruGUIClient(String username, String password);

}

