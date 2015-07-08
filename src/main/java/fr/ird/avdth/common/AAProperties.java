/*
 * $Id: AAProperties.java 556 2015-03-23 13:08:53Z lebranch $
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

/**
 * The AkadoProperties class represents a persistent set of properties. This
 * properties are stored in the file "akado-config.xml".
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 24 juin 2014
 *
 * $LastChangedDate: 2015-03-23 14:08:53 +0100 (lun., 23 mars 2015) $
 *
 * $LastChangedRevision: 556 $
 */
public class AAProperties {

    public static final String KEY_STANDARD_DIRECTORY = "standard_directory";
    public static final String KEY_LOGS_DIRECTORY = "logs_directory";
    public static final String KEY_SHP_COUNTRIES_PATH = "SHP_COUNTRIES";
    public static final String KEY_SHP_OCEAN_PATH = "SHP_IHO_OCEANS";
    public static final String KEY_DATE_FORMAT_XLS = "date_xls";
    public static final String KEY_RESULT_MODEL_AVDTH_XLS = "xls_model_avdth";

    public static final String KEY_SAMPLE_INSPECTOR = "sample_inspector";
    public static final String KEY_WELL_INSPECTOR = "well_inspector";
    public static final String KEY_TRIP_INSPECTOR = "trip_inspector";
    public static final String KEY_POSITION_INSPECTOR = "position_inspector";
    public static final String KEY_ACTIVITY_INSPECTOR = "activity_inspector";
    public static final String KEY_WARNING_INSPECTOR = "warning_inspector";

    public static final String KEY_ANAPO_DB_PATH = "anapo_db";

    public static final String ACTIVE_VALUE = "active";
    public static final String DISABLE_VALUE = "disable";

    public static String DATE_FORMAT_XLS;
    public static String STANDARD_DIRECTORY;
    public static String LOGS_DIRECTORY;
    public static String SHP_COUNTRIES_PATH;
    public static String SHP_OCEAN_PATH;
    public static String RESULT_MODEL_AVDTH_XLS;

    public static String SAMPLE_INSPECTOR;
    public static String WELL_INSPECTOR;
    public static String TRIP_INSPECTOR;
    public static String POSITION_INSPECTOR;
    public static String ACTIVITY_INSPECTOR;
    public static String WARNING_INSPECTOR;

//    public static Properties getDefaultProperties() {
//        Properties p = new Properties();
//        p.setProperty(KEY_SAMPLE_INSPECTOR, ACTIVE_VALUE);
//        p.setProperty(KEY_TRIP_INSPECTOR, ACTIVE_VALUE);
//        p.setProperty(KEY_ACTIVITY_INSPECTOR, ACTIVE_VALUE);
//        p.setProperty(KEY_POSITION_INSPECTOR, ACTIVE_VALUE);
//        p.setProperty(KEY_WELL_INSPECTOR, ACTIVE_VALUE);
//        p.setProperty(KEY_WARNING_INSPECTOR, ACTIVE_VALUE);
//
//        p.setProperty(KEY_STANDARD_DIRECTORY, STANDARD_DIRECTORY);
//        p.setProperty(KEY_LOGS_DIRECTORY, LOGS_DIRECTORY);
//        p.setProperty(KEY_RESULT_MODEL_AVDTH_XLS, RESULT_MODEL_AVDTH_XLS);
//        p.setProperty(KEY_SHP_COUNTRIES_PATH, SHP_COUNTRIES_PATH);
//        p.setProperty(KEY_SHP_OCEAN_PATH, SHP_OCEAN_PATH);
//        p.setProperty(KEY_DATE_FORMAT_XLS, DATE_FORMAT_XLS);
//
//        System.out.println("**************************");
//        System.out.println(p);
//        System.out.println("**************************");
//        return p;
//    }
    public static String ANAPO_DB_URL;

    public static final String KEY_L10N = "lang";
    public static String L10N = "fr";

    public final static String KEY_THRESHOLD_CLASS_ONE = "threshold_class_one";
    public final static  String KEY_THRESHOLD_CLASS_TWO = "threshold_class_two";
    
    public static double THRESHOLD_CLASS_ONE = 15d;
    public static double THRESHOLD_CLASS_TWO = 30d;
}
