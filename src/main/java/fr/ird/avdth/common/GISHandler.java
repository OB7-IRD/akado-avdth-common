/*
 * $Id: GISHandler.java 591 2015-05-04 15:43:29Z lebranch $
 *
 * Copyright (C) 2014 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.avdth.common;

import fr.ird.common.JDBCUtilities;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.h2gis.h2spatialext.CreateSpatialExtension;

/**
 * Utilitaire de création des bases de données GIS pour les règles de
 * validations incluant des zones spatiales. Il repose sur la librairie H2Gis
 * développé au CNRS et charge des données provenant de fichier au format SHP.
 *
 * @see <a href="http://www.h2gis.org/">site de H2Gis</a>
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 30 juin 2014
 *
 * $LastChangedDate: 2015-05-04 17:43:29 +0200 (lun., 04 mai 2015) $
 *
 * $LastChangedRevision: 591 $
 */
public class GISHandler {

    private static final GISHandler service = new GISHandler();
    public static final String OT_DB_GIS_NAME = "OTStandardGIS";

    private String dbPath;
    private String countryShapePath;
    private String oceanShapePath;

    public static GISHandler getService() {
        return service;
    }

    public void init(String directoryPath, String countryShapePath, String oceanShapePath) throws Exception {
        if (directoryPath == null) {
            throw new Exception("The directory path is null.");
        } else {
            this.dbPath = directoryPath + File.separator + OT_DB_GIS_NAME;
        }
        this.countryShapePath = countryShapePath;
        this.oceanShapePath = oceanShapePath;
    }

//    public static boolean GISDBexists() {
//        return new GISHandler().exists(AAProperties.STANDARD_DIRECTORY);
//    }
//    public static void main(String[] args) {
//        if (AAProperties.STANDARD_DIRECTORY == null) {
//            AAProperties.getService().init();
//        }
//        createGISDB();
//    }
//    private final String SHP_RESOURCE_IN_JAR = "fr/ird/akado/avdth/shp";
    /**
     * Vérifie si les bases de données sont déjà créées.
     *
     * @return true if exists
     */
    public boolean exists() {
        return (new File(dbPath + ".h2.db")).exists();
    }

    /**
     * Supprime les bases de données existantes.
     *
     * @return true if the db are deleted
     */
    public boolean delete() {
        return (new File(dbPath + ".h2.db")).delete();
    }

    /**
     * Créer les bases de données GIS en chargeant les données de fichier au
     * format SHP.
     *
     *
     */
    public void create() {

//        System.out.println("File :" + dbPath + ", File exits " + (new File(dbPath + ".h2.db")).exists());
        if (!(new File(dbPath + ".h2.db")).exists()) {
            System.out.println("Create the GIS database.");

            try {
                Class.forName("org.h2.Driver");
                //System.out.println("J'ai chopé le driver");
                try (Connection connection = DriverManager.getConnection("jdbc:h2:" + dbPath);
                        Statement st = connection.createStatement()) {
                    // Import spatial functions, domains and drivers
                    // If you are using a file database, you have to do only that once.
                    //System.out.println("Après STatement");
                    CreateSpatialExtension.initSpatialExtension(connection);
                    System.out.println("SHP_OCEAN_PATH " + oceanShapePath);
                    st.execute("DROP TABLE IF EXISTS seasandoceans;");
                    st.execute("DROP TABLE IF EXISTS tmpseasandoceans;");
                    st.execute("CALL SHPRead('" + oceanShapePath + "', 'tmpseasandoceans');");
//                    st.execute("CALL FILE_TABLE('" + AAProperties.SHP_OCEAN_PATH + "', 'tmpseasandoceans')");
                    st.execute("CREATE TABLE seasandoceans AS SELECT ROW_NUMBER() OVER () AS pkid, * FROM tmpseasandoceans;");
//                    st.execute("CALL SHPRead('" + AAProperties.SHP_OCEAN_PATH + "', 'seasandoceans');");
                    st.execute("ALTER TABLE seasandoceans ALTER COLUMN pkid SET NOT NULL;");
                    st.execute("ALTER TABLE seasandoceans ADD CONSTRAINT pk_ocean_pkid PRIMARY KEY(pkid);");
                    st.execute("CREATE SPATIAL INDEX ocean_spatialindex ON seasandoceans(the_geom);");
                    st.execute("DROP TABLE IF EXISTS tmpseasandoceans;");
                    try (ResultSet rs = st.executeQuery("SELECT count(*) AS rowcount FROM seasandoceans")) {
                        while (rs.next()) {
                            String tmp = "There is " + rs.getInt("rowcount") + " seas and oceans in the database.";
                            System.out.println(tmp);
                        }
                    }
                    //System.out.println("SHP_COUNTRIES_PATH " + AAProperties.SHP_COUNTRIES_PATH);
                    st.execute("DROP TABLE IF EXISTS countries;");
                    st.execute("DROP TABLE IF EXISTS tmpcountries;");
                    st.execute("CALL FILE_TABLE('" + countryShapePath + "', 'tmpcountries')");
                    st.execute("CREATE TABLE countries AS SELECT ROW_NUMBER() OVER ( ) AS pkid, * FROM tmpcountries;");
                    st.execute("ALTER TABLE countries ALTER COLUMN pkid SET NOT NULL;");
                    st.execute("ALTER TABLE countries ADD CONSTRAINT pk_countries_pkid PRIMARY KEY(pkid);");
                    st.execute("CREATE SPATIAL INDEX countries_spatialindex ON countries (the_geom);");
                    st.execute("DROP TABLE IF EXISTS tmpcountries;");
                    try (ResultSet rs = st.executeQuery("SELECT count(*) AS rowcount FROM countries")) {
                        while (rs.next()) {
                            String tmp = "There is " + rs.getInt("rowcount") + " countries in the database.";
                            System.out.println(tmp);
                        }
                    }
                }
            } catch (SQLException ex) {
                JDBCUtilities.printSQLException(ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GISHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Connection connection;

    /**
     *
     * @return une connexion à la base
     */
    public Connection getConnection() {
//        System.out.println("DB Path" + dbPath);
        if (connection == null && dbPath != null) {

            try {
                Class.forName("org.h2.Driver");
                String url = "jdbc:h2:" + dbPath + ";ACCESS_MODE_DATA=R";
                //System.out.println("URL DB GIS " + url);
                connection = DriverManager.getConnection(url);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GISHandler.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                JDBCUtilities.printSQLException(ex);
            }
        }
        return connection;
    }

}
