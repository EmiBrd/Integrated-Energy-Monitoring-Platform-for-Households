package ro.tuc.ds2020.serverPackage;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ro.tuc.ds2020.dtos.ClientsDTO;
import ro.tuc.ds2020.entities.Devices;
import ro.tuc.ds2020.entities.Measurements;
import ro.tuc.ds2020.services.ClientsService;
import ro.tuc.ds2020.services.DevicesService;
import ro.tuc.ds2020.services.MeasurementsService;
import java.text.SimpleDateFormat;
import java.util.*;

public class MeasureValueImpl implements MeasureValue {

    private final MeasurementsService measurementsService;
    private final DevicesService devicesService;
    private final ClientsService clientsService;

    public MeasureValueImpl(MeasurementsService measurementsService, DevicesService devicesService, ClientsService clientsService) {
        this.measurementsService = measurementsService;
        this.devicesService = devicesService;
        this.clientsService = clientsService;
    }

    @Override
    public Double[][] getHourlyEnergConsSubpunct1(Long idClient, Date dataAlegere, Integer ziuaD) {
        System.out.println("Am apelat getHourlyEnergConsSubpunct1");
        List<Measurements> measurementsList = measurementsService.findAllMeasurementsByClientId(idClient);
        List<Devices> devicesList = devicesService.findAllDevicesByClientId(idClient);

        Date dataSfarsit = new Date(dataAlegere.getTime());
        dataSfarsit.setDate(dataAlegere.getDate() - ziuaD);
        dataSfarsit.setHours(0);
        dataAlegere.setHours(dataAlegere.getHours() - 1);

        Integer nrZilePentruAlocare = dataAlegere.getDate() - dataSfarsit.getDate();

        Double[][] matriceRezultat = new Double[24][nrZilePentruAlocare];
        Double[][][] matriceAux = new Double[24][ziuaD][devicesList.size()];
        Double[][][] laCatSaImpart = new Double[24][ziuaD][devicesList.size()];
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < ziuaD; j++) {
                for (int k = 0; k < devicesList.size(); k++) {
                    matriceAux[i][j][k] = 0.0D;
                    laCatSaImpart[i][j][k] = 0.0D;
                }
                matriceRezultat[i][j] = 0.0D;
            }
        }

//        Date dataSfarsit = new Date(dataAlegere.getTime());
//        dataSfarsit.setDate(dataAlegere.getDate() - ziuaD);
//        dataSfarsit.setHours(0);
//        dataAlegere.setHours(dataAlegere.getHours() - 1);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("data alegere bun = " + simpleDateFormat1.format(dataAlegere) );
        Date dataAux = new Date(dataAlegere.getTime());
        System.out.println("dataSfarsit = " + simpleDateFormat1.format(dataSfarsit) + "\n");

        int kappa = 0;
        while (dataAux.getTime() > dataSfarsit.getTime()) {
            for (int j = 23; j >= 0; j--) {
                for (Measurements m : measurementsList) {
                    //System.out.println("dataAux = " + dataAux);
                    if ((m.getTimestamp().getYear() == dataAux.getYear()) &&
                            (m.getTimestamp().getMonth() == dataAux.getMonth()) &&
                            (m.getTimestamp().getDate() == dataAux.getDate()) &&
                            (m.getTimestamp().getHours() == dataAux.getHours())
                    ) {
                        int ji = 0;
                        for (Devices dDto : devicesList) {
                            if (m.getSensor().getDevice().getId() == dDto.getId()) {
                                matriceAux[m.getTimestamp().getHours()][kappa][ji] += m.getEnergyConsumption();
                                laCatSaImpart[m.getTimestamp().getHours()][kappa][ji]++;
                            }
                            ji++;
                        }
                    }
                }
                dataAux.setHours(dataAux.getHours() - 1);
            }
            kappa++;
        }

        for (int i = 0; i < ziuaD; i++) {
            for (int j = 0; j < 24; j++) {
                for (int k = 0; k < devicesList.size(); k++) {
                    if (laCatSaImpart[j][i][k] > 0) {
                        matriceAux[j][i][k] /= laCatSaImpart[j][i][k];
                        matriceRezultat[j][i] += matriceAux[j][i][k];
                    }
                }
            }
        }

//        for(int i = 0; i<ziuaD; i++){
//            for(int j = 0; j<24; j++){
//                System.out.println("matrice rezultat["+j+"]["+i+"] = " + matriceRezultat[j][i]);
//            }
//        }

        return matriceRezultat;
    }


    @Override
    public Double[] getAverageEnergConsSubpunct2(Long idClient) {
        System.out.println("Am apelat getAverageEnergConsSubpunct2");
        Date dataAzi = new Date();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("dataAzi = " + simpleDateFormat1.format(dataAzi) );
        dataAzi.setHours(0);
        dataAzi.setHours(dataAzi.getHours() - 1);
        System.out.println("ieri incepand cu ora 23 = " + simpleDateFormat1.format(dataAzi) );
        Double[] averagedEnergCons = devicesService.valoriMasuratoriSubpunct2(idClient, dataAzi, 7);
        //String[] vectorRezultat = new String[24];
        //for(int i =0; i<24; i++){
        //    vectorRezultat[i] = String.valueOf(averagedEnergCons[i]);
        //System.out.println("averagedEnergCons["+i+"] = " + averagedEnergCons[i]);
        //}
        return averagedEnergCons;
    }


    @Override
    public List<String[]> sendDevicesToClient(Long idClient){
        List<Devices> devicesList = devicesService.findAllDevicesByClientId(idClient);
        List<String[]> devicesListGood = new ArrayList<>();
        for(Devices d: devicesList){
            devicesListGood.add(new String[]{
                    (String.valueOf(d.getId())), String.valueOf(d.getDescription()), String.valueOf(d.getAddressLocation()),
                    String.valueOf(d.getMaxEnerCons()), String.valueOf(d.getAvgBaselineEnerCons())
            });
        }
        return devicesListGood;
    }



    @Override
    public Integer[] getBestTimeToStartSubpunct4(Long idClient, Integer nrOreProgram) {
        System.out.println("Am apelat getBestTimeToStartSubpunct4");
        Date dataAzi = new Date();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("dataAzi = " + simpleDateFormat1.format(dataAzi) );
        dataAzi.setHours(0);
        dataAzi.setHours(dataAzi.getHours() - 1);
        System.out.println("ieri incepand cu ora 23 = " + simpleDateFormat1.format(dataAzi) );
        Double[] averagedEnergCons = devicesService.valoriMasuratoriSubpunct2(idClient, dataAzi, 7);

        int[] orePosibileDeStart = new int[24];
        Double[] valoriMinime = new Double[24];

        for(int i = 0; i<24; i++){
            orePosibileDeStart[i] = -1;
            valoriMinime[i] = 999999.0D;
        }

        //System.out.println("averagedEnergCons[0] = " + averagedEnergCons[0]);
        for(int i = 1; i<23; i++){
            //System.out.println("averagedEnergCons["+i+"] = " + averagedEnergCons[i]);
            if( (averagedEnergCons[i] < averagedEnergCons[i-1]) && (averagedEnergCons[i] < averagedEnergCons[i+1]) ){
                orePosibileDeStart[i] = i;
                valoriMinime[i] = averagedEnergCons[i];
            }
        }
        //System.out.println("averagedEnergCons[23] = " + averagedEnergCons[23]);

        Double mini = valoriMinime[0];
        Integer oraStart = -1;
        for(int i = 0; i<24; i++){
            if(orePosibileDeStart[i] >= 0){
                if(valoriMinime[i] < mini){
                    oraStart = i;
                    mini = valoriMinime[i];
                }
            }
        }

        Integer oraEnd = oraStart + nrOreProgram;
        oraEnd = oraEnd % 24;
        System.out.println("oraStart = " + oraStart + ", oraEnd = " + oraEnd);
        System.out.println("mini = " + mini);
        return new Integer[]{oraStart, oraEnd};
    }


    @Override
    public String[] loginPentruGUIClient(String username, String password){
        //ClientsDTO clientDTO = clientsService.findClientByCredentials(username, password);
        ClientsDTO clientDTO = clientsService.findClientByUsernameDTO(username);
        if(clientDTO == null){
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(password, clientDTO.getPassword())==true) {
            clientDTO.setActive(1);
            System.out.println("user-ul cu username = " + clientDTO.getUsername() + ", id = " + clientDTO.getId() +
                    ", parola = " + password + ", s-a logat\n");
        }
        return new String[]{clientDTO.getUsername(), password, String.valueOf(clientDTO.getId()) };
    }


}
