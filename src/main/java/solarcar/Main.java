package solarcar;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Replace URL and APIKEY with their corresponding values when running
        InfluxDBClient db = InfluxDBClientFactory.create("URL", "APIKEY".toCharArray(), "ORGNAME","Cardata");

        WriteApiBlocking writeApi = db.getWriteApiBlocking();
        Random random = new Random();

        // Telemetry objects
        DCBus dcbus = new DCBus();
        Velocity velocity = new Velocity();
        odoAndAmp odoAndAmp = new odoAndAmp();
        DriveCMD driveCMD = new DriveCMD();
        MainPackInfo mainPackInfo = new MainPackInfo();
        PackTempInfo packTempInfo = new PackTempInfo();
        PackVoltInfo packVoltInfo = new PackVoltInfo();


        while(true){

            // write dc bus data
            dcbus.volt = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            dcbus.dcCurrent = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            writeApi.writeMeasurement(WritePrecision.MS, dcbus);

            // write velocity data
            velocity.vel = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            velocity.motorRPM = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            writeApi.writeMeasurement(WritePrecision.MS, velocity);

            // write odo&amp data
            odoAndAmp.amphours = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            odoAndAmp.odo = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            writeApi.writeMeasurement(WritePrecision.MS, odoAndAmp);

            // write driveCMD data
            driveCMD.motorCurrent = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            driveCMD.motorRPM = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            writeApi.writeMeasurement(WritePrecision.MS, driveCMD);

            // write mainPackInfo data
            mainPackInfo.summedV = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            mainPackInfo.packAmp = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            mainPackInfo.mainPackCurrent = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            mainPackInfo.instVolt = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            writeApi.writeMeasurement(WritePrecision.MS, mainPackInfo);

            // write packTempInfo
            packTempInfo.avgTemp = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            packTempInfo.highTemp = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            packTempInfo.lowTemp = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            writeApi.writeMeasurement(WritePrecision.MS, packTempInfo);

            // write packVoltageInfo
            packVoltInfo.avgCellV = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            packVoltInfo.hiCellID = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            packVoltInfo.hiCellV = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            packVoltInfo.lowCellV = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            packVoltInfo.lowCellID = (double) Math.round(random.nextDouble(100) * 1000) / 1000;
            writeApi.writeMeasurement(WritePrecision.MS, packVoltInfo);


            try {
                Thread.sleep(150);
                System.out.println("New Data Written");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
    @Measurement(name = "velocity")
    private static class Velocity {
        @Column(name = "vel")
        double vel;

        @Column(name = "velMotorRPM")
        double motorRPM;
    }

    @Measurement(name = "mainPackInfo")
    private static class MainPackInfo {
        @Column(name = "mainPackCurrent")
        double mainPackCurrent;

        @Column(name = "instVolt")
        double instVolt;

        @Column(name = "packAmp")
        double packAmp;

        @Column(name = "summedV")
        double summedV;
    }

    @Measurement(name = "dcBus")
    private static class DCBus {
        @Column(name = "dcCurrent")
        double dcCurrent;

        @Column(name = "dcVoltage")
        double volt;

    }

    @Measurement(name = "odoAndAmp")
    private static class odoAndAmp {
        @Column(name = "amphours")
        double amphours;

        @Column(name = "odo")
        double odo;

    }

    @Measurement(name = "packTempInfo")
    private static class PackTempInfo {
        @Column(name = "lowTemp")
        double lowTemp;

        @Column(name = "highTemp")
        double highTemp;

        @Column(name = "avgTemp")
        double avgTemp;


    }

    @Measurement(name = "packVoltInfo")
    private static class PackVoltInfo {
        @Column(name = "hiCellV")
        double hiCellV;

        @Column(name = "lowCellV")
        double lowCellV;

        @Column(name = "avgCellV")
        double avgCellV;

        @Column(name = "hiCellID")
        double hiCellID;

        @Column(name = "lowCellID")
        double lowCellID;
    }

    @Measurement(name = "driveCMD")
    private static class DriveCMD{
        @Column(name = "motorCurrent")
        double motorCurrent;

        @Column(name = "driveMotorRPM")
        double motorRPM;
    }
}