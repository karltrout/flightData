import com.databricks.spark.avro.SchemaConverters;

import org.apache.spark.sql.types.ArrayType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 * Created by karlt on 11/15/17.
 */
public class TfmsFlowMessageSchema {

  private static final StructType structType = new StructType(new StructField[]{
      new StructField("_acid", DataTypes.StringType, true, Metadata.empty()),
      new StructField("_airline", DataTypes.StringType, true, Metadata.empty()),
      new StructField("_arrArpt", DataTypes.StringType, true, Metadata.empty()),
      new StructField("_cdmPart", DataTypes.BooleanType, true, Metadata.empty()),
      new StructField("_depArpt", DataTypes.StringType, true, Metadata.empty()),
      new StructField("_fdTrigger", DataTypes.StringType, true, Metadata.empty()),
      new StructField("_flightRef", DataTypes.LongType, true, Metadata.empty()),
      new StructField("_msgType", DataTypes.StringType, true, Metadata.empty()),
      new StructField("_sensitivity", DataTypes.StringType, true, Metadata.empty()),
      new StructField("_sourceFacility", DataTypes.StringType, true, Metadata.empty()),
      new StructField("_sourceTimeStamp", DataTypes.StringType, true, Metadata.empty()),
      new StructField("fdm:arrivalInformation", new StructType(new StructField[]{
          new StructField("nxcm:ncsmFlightTimeData",
                          new StructType(
                              new StructField[]{
                                  new StructField("nxcm:eta",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "_VALUE",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_etaType",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_timeValue",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty())}),
                                                  true,
                                                  Metadata.empty()),
                                  new StructField("nxcm:etd",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "_VALUE",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_etdType",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_timeValue",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty())}),
                                                  true,
                                                  Metadata.empty()),
                                  new StructField("nxcm:rvsmData",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "_VALUE",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_currentCompliance",
                                                              DataTypes.BooleanType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_equipped",
                                                              DataTypes.BooleanType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_futureCompliance",
                                                              DataTypes.BooleanType,
                                                              true,
                                                              Metadata
                                                                  .empty())}),
                                                  true, Metadata
                                                      .empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:qualifiedAircraftId", new StructType(
              new StructField[]{
                  new StructField("nxce:aircraftId", DataTypes.StringType, true, Metadata.empty()),
                  new StructField("nxce:arrivalPoint", new StructType(new StructField[]{
                      new StructField("nxce:airport", DataTypes.StringType, true, Metadata.empty()),
                      new StructField("nxce:fix", new StructType(new StructField[]{
                          new StructField("nxce:namedFix", DataTypes.StringType, true,
                                          Metadata.empty())}), true, Metadata.empty())}), true,
                                  Metadata.empty()),
                  new StructField("nxce:computerId", new StructType(new StructField[]{
                      new StructField("nxce:facilityIdentifier", DataTypes.StringType, true,
                                      Metadata.empty()),
                      new StructField("nxce:idNumber", DataTypes.LongType, true, Metadata.empty())}), true,
                                  Metadata.empty()),
                  new StructField("nxce:departurePoint", new StructType(new StructField[]{
                      new StructField("nxce:airport", DataTypes.StringType, true, Metadata.empty()),
                      new StructField("nxce:fix", new StructType(new StructField[]{
                          new StructField("nxce:namedFix", DataTypes.StringType, true,
                                          Metadata.empty())}), true, Metadata.empty())}), true,
                                  Metadata.empty()),
                  new StructField("nxce:gufi", DataTypes.StringType, true, Metadata.empty()),
                  new StructField("nxce:igtd", DataTypes.StringType, true, Metadata.empty())}), true,
                          Metadata.empty()),
          new StructField("nxcm:timeOfArrival", new StructType(
              new StructField[]{
                  new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                  new StructField("_estimated", DataTypes.BooleanType, true, Metadata.empty())}), true,
                          Metadata.empty())}), true, Metadata.empty()),
      new StructField("fdm:boundaryCrossingUpdate", new StructType(new StructField[]{
          new StructField("nxcm:boundaryPosition", new StructType(
              new StructField[]{
                  new StructField("_boundaryCrossingTime", DataTypes.StringType, true, Metadata.empty()),
                  new StructField("nxce:fixRadialDistance", new StructType(new StructField[]{
                      new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                      new StructField("_distance", DataTypes.LongType, true, Metadata.empty()),
                      new StructField("_radial", DataTypes.LongType, true, Metadata.empty())}), true,
                                  Metadata.empty()),
                  new StructField("nxce:latLong",
                                  new StructType(new StructField[]{
                                      new StructField("nxce:latitude",
                                                      new StructType(new StructField[]{
                                                          new StructField("nxce:latitudeDMS",
                                                                          new StructType(
                                                                              new StructField[]{
                                                                                  new StructField(
                                                                                      "_VALUE",
                                                                                      DataTypes.StringType,
                                                                                      true,
                                                                                      Metadata.empty()),
                                                                                  new StructField(
                                                                                      "_degrees",
                                                                                      DataTypes.LongType,
                                                                                      true,
                                                                                      Metadata.empty()),
                                                                                  new StructField(
                                                                                      "_direction",
                                                                                      DataTypes.StringType,
                                                                                      true,
                                                                                      Metadata.empty()),
                                                                                  new StructField(
                                                                                      "_minutes",
                                                                                      DataTypes.LongType,
                                                                                      true,
                                                                                      Metadata.empty()),
                                                                                  new StructField(
                                                                                      "_seconds",
                                                                                      DataTypes.LongType,
                                                                                      true,
                                                                                      Metadata.empty())
                                                                              }), true, Metadata.empty())
                                                      }), true, Metadata.empty()),
                                      new StructField("nxce:longitude",
                                                      new StructType(new StructField[]{
                                                          new StructField("nxce:longitudeDMS",
                                                                          new StructType(
                                                                              new StructField[]{
                                                                                  new StructField(
                                                                                      "_VALUE",
                                                                                      DataTypes.StringType,
                                                                                      true,
                                                                                      Metadata.empty()),
                                                                                  new StructField(
                                                                                      "_degrees",
                                                                                      DataTypes.LongType,
                                                                                      true,
                                                                                      Metadata.empty()),
                                                                                  new StructField(
                                                                                      "_direction",
                                                                                      DataTypes.StringType,
                                                                                      true,
                                                                                      Metadata.empty()),
                                                                                  new StructField(
                                                                                      "_minutes",
                                                                                      DataTypes.LongType,
                                                                                      true,
                                                                                      Metadata.empty()),
                                                                                  new StructField(
                                                                                      "_seconds",
                                                                                      DataTypes.LongType,
                                                                                      true,
                                                                                      Metadata.empty())
                                                                              }), true, Metadata.empty())
                                                      }), true, Metadata.empty())
                                  }), true, Metadata.empty()),
                  new StructField("nxce:namedFix", DataTypes.StringType, true, Metadata.empty())}), true,
                          Metadata.empty()),
          new StructField("nxcm:flightAircraftSpecs", new StructType(
              new StructField[]{
                  new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                  new StructField("_equipmentQualifier", DataTypes.StringType, true, Metadata.empty()),
                  new StructField("_specialAircraftQualifier", DataTypes.StringType, true,
                                  Metadata.empty())}), true, Metadata.empty()),
          new StructField("nxcm:ncsmRouteData", new StructType(new StructField[]{
              new StructField("nxcm:arrivalFixAndTime", new StructType(
                  new StructField[]{
                      new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                      new StructField("_arrTime", DataTypes.StringType, true, Metadata.empty()),
                      new StructField("_fixName", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:departureFixAndTime", new StructType(
                  new StructField[]{
                      new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                      new StructField("_arrTime", DataTypes.StringType, true, Metadata.empty()),
                      new StructField("_fixName", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:diversionIndicator", DataTypes.StringType, true, Metadata.empty()),
              new StructField("nxcm:dp", new StructType(
                  new StructField[]{
                      new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                      new StructField("_routeName", DataTypes.StringType, true, Metadata.empty()),
                      new StructField("_routeType", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:dpTransitionFix", DataTypes.StringType, true, Metadata.empty()),
              new StructField("nxcm:eta", new StructType(
                  new StructField[]{
                      new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                      new StructField("_etaType", DataTypes.StringType, true, Metadata.empty()),
                      new StructField("_timeValue", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:etd", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_etdType", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_timeValue", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:flightTraversalData2", new StructType(
                  new StructField[]{
                      new StructField("nxce:airway", new ArrayType(
                          new StructType(new StructField[]{
                              new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                              new StructField("_sequenceNumber", DataTypes.LongType, true, Metadata.empty())
                          }), true),
                                      true, Metadata.empty()),
                      new StructField("nxce:center", new ArrayType(new StructType(
                          new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                            new StructField("_elapsedEntryTime", DataTypes.LongType, true,
                                                            Metadata.empty()),
                                            new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                            Metadata.empty())}), true),
                                      true, Metadata.empty()),
                      new StructField("nxce:fix", new ArrayType(new StructType(
                          new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                            new StructField("_elapsedTime", DataTypes.LongType, true, Metadata.empty()),
                                            new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                            Metadata.empty())}), true),
                                      true, Metadata.empty()),
                      new StructField("nxce:sector", new ArrayType(new StructType(
                          new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                            new StructField("_elapsedEntryTime", DataTypes.LongType, true,
                                                            Metadata.empty()),
                                            new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                            Metadata.empty())}), true),
                                      true, Metadata.empty()),
                      new StructField("nxce:waypoint", new ArrayType(new StructType(
                          new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                            new StructField("_elapsedTime", DataTypes.LongType, true, Metadata.empty()),
                                            new StructField("_latitudeDecimal", DataTypes.DoubleType, true,
                                                            Metadata.empty()),
                                            new StructField("_longitudeDecimal", DataTypes.DoubleType, true,
                                                            Metadata.empty()),
                                            new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                            Metadata.empty())}), true),
                                      true, Metadata.empty())}), true, Metadata.empty()),
              new StructField("nxcm:nextPosition", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_latitudeDecimal", DataTypes.DoubleType, true, Metadata.empty()),
                                    new StructField("_longitudeDecimal", DataTypes.DoubleType, true,
                                                    Metadata.empty())}), true, Metadata.empty()),
              new StructField("nxcm:remarksKeywords", DataTypes.StringType, true, Metadata.empty()),
              new StructField("nxcm:routeOfFlight", DataTypes.StringType, true, Metadata.empty()),
              new StructField("nxcm:rvsmData", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_currentCompliance", DataTypes.BooleanType, true,
                                                    Metadata.empty()),
                                    new StructField("_equipped", DataTypes.BooleanType, true, Metadata.empty()),
                                    new StructField("_futureCompliance", DataTypes.BooleanType, true,
                                                    Metadata.empty())}), true, Metadata.empty()),
              new StructField("nxcm:star", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_routeName", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_routeType", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:starTransitionFix", DataTypes.StringType, true, Metadata.empty())}), true,
                          Metadata.empty()),
          new StructField("nxcm:qualifiedAircraftId", new StructType(
              new StructField[]{new StructField("nxce:aircraftId", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("nxce:arrivalPoint", new StructType(new StructField[]{
                                    new StructField("nxce:airport", DataTypes.StringType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:computerId", new StructType(new StructField[]{
                                    new StructField("nxce:facilityIdentifier", DataTypes.StringType, true,
                                                    Metadata.empty()),
                                    new StructField("nxce:idNumber", DataTypes.LongType, true, Metadata.empty())
                                }), true, Metadata.empty()),
                                new StructField("nxce:departurePoint", new StructType(new StructField[]{
                                    new StructField("nxce:airport", DataTypes.StringType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:gufi", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("nxce:igtd", DataTypes.StringType, true, Metadata.empty())}), true,
                          Metadata.empty()),
          new StructField("nxcm:reportedAltitude", new StructType(new StructField[]{
              new StructField("nxce:assignedAltitude", new StructType(new StructField[]{
                  new StructField("nxce:blockedAltitude", new StructType(
                      new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                        new StructField("_max", DataTypes.LongType, true, Metadata.empty()),
                                        new StructField("_min", DataTypes.LongType, true, Metadata.empty())}), true,
                                  Metadata.empty()),
                  new StructField("nxce:simpleAltitude", DataTypes.LongType, true, Metadata.empty())}), true,
                              Metadata.empty())}), true, Metadata.empty()),
          new StructField("nxcm:routeOfFlight", new StructType(
              new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("_legacyFormat", DataTypes.StringType, true, Metadata.empty())}), true,
                          Metadata.empty()),
          new StructField("nxcm:speed", new StructType(new StructField[]{
              new StructField("nxce:filedTrueAirSpeed", DataTypes.LongType, true, Metadata.empty()),
              new StructField("nxce:mach", DataTypes.LongType, true, Metadata.empty())}), true, Metadata.empty())}),
                      true, Metadata.empty()),
      new StructField("fdm:departureInformation", new StructType(new StructField[]{
          new StructField("nxcm:flightAircraftSpecs", new StructType(
              new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("_equipmentQualifier", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("_specialAircraftQualifier", DataTypes.StringType, true,
                                                Metadata.empty())}), true, Metadata.empty()),
          new StructField("nxcm:ncsmFlightTimeData", new StructType(new StructField[]{new StructField("nxcm:eta",
                                                                                                      new StructType(
                                                                                                          new StructField[]{
                                                                                                              new StructField(
                                                                                                                  "_VALUE",
                                                                                                                  DataTypes.StringType,
                                                                                                                  true,
                                                                                                                  Metadata
                                                                                                                      .empty()),
                                                                                                              new StructField(
                                                                                                                  "_etaType",
                                                                                                                  DataTypes.StringType,
                                                                                                                  true,
                                                                                                                  Metadata
                                                                                                                      .empty()),
                                                                                                              new StructField(
                                                                                                                  "_timeValue",
                                                                                                                  DataTypes.StringType,
                                                                                                                  true,
                                                                                                                  Metadata
                                                                                                                      .empty())}),
                                                                                                      true,
                                                                                                      Metadata.empty()),
                                                                                      new StructField("nxcm:etd",
                                                                                                      new StructType(
                                                                                                          new StructField[]{
                                                                                                              new StructField(
                                                                                                                  "_VALUE",
                                                                                                                  DataTypes.StringType,
                                                                                                                  true,
                                                                                                                  Metadata
                                                                                                                      .empty()),
                                                                                                              new StructField(
                                                                                                                  "_etdType",
                                                                                                                  DataTypes.StringType,
                                                                                                                  true,
                                                                                                                  Metadata
                                                                                                                      .empty()),
                                                                                                              new StructField(
                                                                                                                  "_timeValue",
                                                                                                                  DataTypes.StringType,
                                                                                                                  true,
                                                                                                                  Metadata
                                                                                                                      .empty())}),
                                                                                                      true,
                                                                                                      Metadata.empty()),
                                                                                      new StructField("nxcm:rvsmData",
                                                                                                      new StructType(
                                                                                                          new StructField[]{
                                                                                                              new StructField(
                                                                                                                  "_VALUE",
                                                                                                                  DataTypes.StringType,
                                                                                                                  true,
                                                                                                                  Metadata
                                                                                                                      .empty()),
                                                                                                              new StructField(
                                                                                                                  "_currentCompliance",
                                                                                                                  DataTypes.BooleanType,
                                                                                                                  true,
                                                                                                                  Metadata
                                                                                                                      .empty()),
                                                                                                              new StructField(
                                                                                                                  "_equipped",
                                                                                                                  DataTypes.BooleanType,
                                                                                                                  true,
                                                                                                                  Metadata
                                                                                                                      .empty()),
                                                                                                              new StructField(
                                                                                                                  "_futureCompliance",
                                                                                                                  DataTypes.BooleanType,
                                                                                                                  true,
                                                                                                                  Metadata
                                                                                                                      .empty())}),
                                                                                                      true, Metadata
                                                                                                          .empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:qualifiedAircraftId", new StructType(
              new StructField[]{new StructField("nxce:aircraftId", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("nxce:arrivalPoint", new StructType(new StructField[]{
                                    new StructField("nxce:airport", DataTypes.StringType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:computerId", new StructType(new StructField[]{
                                    new StructField("nxce:facilityIdentifier", DataTypes.StringType, true,
                                                    Metadata.empty()),
                                    new StructField("nxce:idNumber", DataTypes.LongType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:departurePoint", new StructType(new StructField[]{
                                    new StructField("nxce:airport", DataTypes.StringType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:gufi", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("nxce:igtd", DataTypes.StringType, true, Metadata.empty())}), true,
                          Metadata.empty()),
          new StructField("nxcm:timeOfArrival", new StructType(
              new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("_estimated", DataTypes.BooleanType, true, Metadata.empty())}), true,
                          Metadata.empty()),
          new StructField("nxcm:timeOfDeparture", new StructType(
              new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("_estimated", DataTypes.BooleanType, true, Metadata.empty())}), true,
                          Metadata.empty())}), true, Metadata.empty()),
      new StructField("fdm:flightPlanAmendmentInformation", new StructType(new StructField[]{
          new StructField("nxcm:amendmentData", new StructType(new StructField[]{
              new StructField("nxcm:newAltitude",
                              new StructType(
                                  new StructField[]{
                                      new StructField(
                                          "nxce:assignedAltitude",
                                          new StructType(
                                              new StructField[]{
                                                  new StructField(
                                                      "nxce:altitudeFixAltitude",
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_postFixAltitude",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_preFixAltitude",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "nxce:namedFix",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "nxce:blockedAltitude",
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_max",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_min",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "nxce:simpleAltitude",
                                                      DataTypes.LongType,
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "nxce:visualFlightRules",
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_vfrOnTop",
                                                                  DataTypes.BooleanType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true,
                                                      Metadata
                                                          .empty())}),
                                          true,
                                          Metadata
                                              .empty()),
                                      new StructField(
                                          "nxce:requestedAltitude",
                                          new StructType(
                                              new StructField[]{
                                                  new StructField(
                                                      "nxce:simpleAltitude",
                                                      DataTypes.LongType,
                                                      true,
                                                      Metadata
                                                          .empty())}),
                                          true,
                                          Metadata
                                              .empty())}),
                              true,
                              Metadata.empty()),
              new StructField(
                  "nxcm:newCoordinationPoint",
                  new StructType(new StructField[]{
                      new StructField(
                          "nxce:fixRadialDistance",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "_distance",
                                      DataTypes.LongType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "_radial",
                                      DataTypes.LongType,
                                      true, Metadata
                                          .empty())}),
                          true, Metadata.empty()),
                      new StructField("nxce:latLong",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:latitude",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "nxce:latitudeDMS",
                                                              new StructType(
                                                                  new StructField[]{
                                                                      new StructField(
                                                                          "_VALUE",
                                                                          DataTypes.StringType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_degrees",
                                                                          DataTypes.LongType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_direction",
                                                                          DataTypes.StringType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_minutes",
                                                                          DataTypes.LongType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_seconds",
                                                                          DataTypes.LongType,
                                                                          true,
                                                                          Metadata
                                                                              .empty())
                                                                  }),
                                                              true,
                                                              Metadata
                                                                  .empty())
                                                      }),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:longitude",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "nxce:longitudeDMS",
                                                              new StructType(
                                                                  new StructField[]{
                                                                      new StructField(
                                                                          "_VALUE",
                                                                          DataTypes.StringType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_degrees",
                                                                          DataTypes.LongType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_direction",
                                                                          DataTypes.StringType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_minutes",
                                                                          DataTypes.LongType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_seconds",
                                                                          DataTypes.LongType,
                                                                          true,
                                                                          Metadata
                                                                              .empty())
                                                                  }),
                                                              true,
                                                              Metadata
                                                                  .empty())
                                                      }),
                                                  true,
                                                  Metadata
                                                      .empty())
                                          }), true,
                                      Metadata
                                          .empty()),
                      new StructField(
                          "nxce:namedFix",
                          DataTypes.StringType, true,
                          Metadata.empty())}), true,
                  Metadata.empty()),
              new StructField(
                  "nxcm:newCoordinationTime",
                  new StructType(new StructField[]{
                      new StructField("_VALUE",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                      new StructField("_type",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty())}),
                  true, Metadata.empty()),
              new StructField(
                  "nxcm:newFlightAircraftSpecs",
                  new StructType(new StructField[]{
                      new StructField("_VALUE",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                      new StructField(
                          "_equipmentQualifier",
                          DataTypes.StringType, true,
                          Metadata.empty()),
                      new StructField(
                          "_specialAircraftQualifier",
                          DataTypes.StringType, true,
                          Metadata.empty())}), true,
                  Metadata.empty()),
              new StructField(
                  "nxcm:newRouteOfFlight",
                  new StructType(new StructField[]{
                      new StructField("_VALUE",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                      new StructField(
                          "_legacyFormat",
                          DataTypes.StringType, true,
                          Metadata.empty())}), true,
                  Metadata.empty()),
              new StructField("nxcm:newSpeed",
                              new StructType(
                                  new StructField[]{
                                      new StructField(
                                          "nxce:filedTrueAirSpeed",
                                          DataTypes.LongType,
                                          true,
                                          Metadata
                                              .empty()),
                                      new StructField(
                                          "nxce:mach",
                                          DataTypes.LongType,
                                          true,
                                          Metadata
                                              .empty())}),
                              true,
                              Metadata.empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:ncsmRouteData", new StructType(new StructField[]{
              new StructField("nxcm:arrivalFixAndTime", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_arrTime", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_fixName", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:departureFixAndTime", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_arrTime", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_fixName", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:diversionIndicator", DataTypes.StringType, true, Metadata.empty()),
              new StructField("nxcm:dp", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_routeName", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_routeType", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:dpTransitionFix", DataTypes.StringType, true, Metadata.empty()),
              new StructField("nxcm:eta", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_etaType", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_timeValue", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:etd", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_etdType", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_timeValue", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:flightTraversalData2", new StructType(new StructField[]{
                  new StructField("nxce:airway", new ArrayType(new StructType(
                      new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                        new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                        Metadata.empty())}), true),
                                  true, Metadata.empty()),
                  new StructField("nxce:center", new ArrayType(new StructType(
                      new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                        new StructField("_elapsedEntryTime", DataTypes.LongType, true,
                                                        Metadata.empty()),
                                        new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                        Metadata.empty())}), true),
                                  true, Metadata.empty()),
                  new StructField("nxce:fix", new ArrayType(new StructType(
                      new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                        new StructField("_elapsedTime", DataTypes.LongType, true, Metadata.empty()),
                                        new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                        Metadata.empty())}), true),
                                  true, Metadata.empty()),
                  new StructField("nxce:sector", new ArrayType(new StructType(
                      new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                        new StructField("_elapsedEntryTime", DataTypes.LongType, true,
                                                        Metadata.empty()),
                                        new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                        Metadata.empty())}), true),
                                  true, Metadata.empty()),
                  new StructField("nxce:waypoint", new ArrayType(new StructType(
                      new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                        new StructField("_elapsedTime", DataTypes.LongType, true, Metadata.empty()),
                                        new StructField("_latitudeDecimal", DataTypes.DoubleType, true,
                                                        Metadata.empty()),
                                        new StructField("_longitudeDecimal", DataTypes.DoubleType, true,
                                                        Metadata.empty()),
                                        new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                        Metadata.empty())}), true),
                                  true, Metadata.empty())}), true, Metadata.empty()),
              new StructField("nxcm:nextPosition", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_latitudeDecimal", DataTypes.DoubleType, true, Metadata.empty()),
                                    new StructField("_longitudeDecimal", DataTypes.DoubleType, true,
                                                    Metadata.empty())}), true, Metadata.empty()),
              new StructField("nxcm:remarksKeywords", DataTypes.StringType, true, Metadata.empty()),
              new StructField("nxcm:routeOfFlight", DataTypes.StringType, true, Metadata.empty()),
              new StructField("nxcm:rvsmData", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_currentCompliance", DataTypes.BooleanType, true,
                                                    Metadata.empty()),
                                    new StructField("_equipped", DataTypes.BooleanType, true, Metadata.empty()),
                                    new StructField("_futureCompliance", DataTypes.BooleanType, true,
                                                    Metadata.empty())}), true, Metadata.empty()),
              new StructField("nxcm:star", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_routeName", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_routeType", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:starTransitionFix", DataTypes.StringType, true, Metadata.empty())}), true,
                          Metadata.empty()),
          new StructField("nxcm:newAircraftId", DataTypes.StringType, true, Metadata.empty()),
          new StructField("nxcm:qualifiedAircraftId", new StructType(
              new StructField[]{new StructField("nxce:aircraftId", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("nxce:arrivalPoint", new StructType(new StructField[]{
                                    new StructField("nxce:airport", DataTypes.StringType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:computerId", new StructType(new StructField[]{
                                    new StructField("nxce:facilityIdentifier", DataTypes.StringType, true,
                                                    Metadata.empty()),
                                    new StructField("nxce:idNumber", DataTypes.LongType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:departurePoint", new StructType(new StructField[]{
                                    new StructField("nxce:airport", DataTypes.StringType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:gufi", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("nxce:igtd", DataTypes.StringType, true, Metadata.empty())}), true,
                          Metadata.empty())}), true, Metadata.empty()),
      new StructField("fdm:flightPlanCancellation", new StructType(new StructField[]{
          new StructField("nxcm:qualifiedAircraftId", new StructType(
              new StructField[]{new StructField("nxce:aircraftId", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("nxce:arrivalPoint", new StructType(new StructField[]{
                                    new StructField("nxce:airport", DataTypes.StringType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:computerId", new StructType(new StructField[]{
                                    new StructField("nxce:facilityIdentifier", DataTypes.StringType, true,
                                                    Metadata.empty()),
                                    new StructField("nxce:idNumber", DataTypes.LongType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:departurePoint", new StructType(new StructField[]{
                                    new StructField("nxce:airport", DataTypes.StringType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:gufi", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("nxce:igtd", DataTypes.StringType, true, Metadata.empty())}), true,
                          Metadata.empty())}), true, Metadata.empty()),
      new StructField("fdm:flightPlanInformation", new StructType(new StructField[]{
          new StructField("nxcm:altitude",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:assignedAltitude",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:blockedAltitude",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "_VALUE",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_max",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_min",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty())}),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:simpleAltitude",
                                                  DataTypes.LongType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:requestedAltitude",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:simpleAltitude",
                                                  DataTypes.LongType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
          new StructField(
              "nxcm:coordinationPoint",
              new StructType(
                  new StructField[]{
                      new StructField(
                          "nxce:fixRadialDistance",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_distance",
                                      DataTypes.LongType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_radial",
                                      DataTypes.LongType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxce:latLong",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:latitude",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:latitudeDMS",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "_VALUE",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_degrees",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_direction",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_minutes",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_seconds",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty())
                                                      }),
                                                  true,
                                                  Metadata
                                                      .empty())
                                          }),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:longitude",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:longitudeDMS",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "_VALUE",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_degrees",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_direction",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_minutes",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_seconds",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty())
                                                      }),
                                                  true,
                                                  Metadata
                                                      .empty())
                                          }),
                                      true,
                                      Metadata
                                          .empty())
                              }), true,
                          Metadata.empty()),
                      new StructField(
                          "nxce:namedFix",
                          DataTypes.StringType,
                          true,
                          Metadata.empty())}),
              true, Metadata.empty()),
          new StructField(
              "nxcm:coordinationTime",
              new StructType(
                  new StructField[]{
                      new StructField(
                          "_VALUE",
                          DataTypes.StringType,
                          true,
                          Metadata.empty()),
                      new StructField("_type",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty())}),
              true, Metadata.empty()),
          new StructField(
              "nxcm:flightAircraftSpecs",
              new StructType(
                  new StructField[]{
                      new StructField(
                          "_VALUE",
                          DataTypes.StringType,
                          true,
                          Metadata.empty()),
                      new StructField(
                          "_equipmentQualifier",
                          DataTypes.StringType,
                          true,
                          Metadata.empty()),
                      new StructField(
                          "_numberOfAircraft",
                          DataTypes.LongType,
                          true,
                          Metadata.empty()),
                      new StructField(
                          "_specialAircraftQualifier",
                          DataTypes.StringType,
                          true,
                          Metadata.empty())}),
              true, Metadata.empty()),
          new StructField(
              "nxcm:ncsmRouteData",
              new StructType(
                  new StructField[]{
                      new StructField(
                          "nxcm:arrivalFixAndTime",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_arrTime",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_fixName",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:departureFixAndTime",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_arrTime",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_fixName",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:diversionIndicator",
                          DataTypes.StringType,
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:dp",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_routeName",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_routeType",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:dpTransitionFix",
                          DataTypes.StringType,
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:eta",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_etaType",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_timeValue",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:etd",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_etdType",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_timeValue",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:flightTraversalData2",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:airway",
                                      new ArrayType(
                                          new StructType(
                                              new StructField[]{
                                                  new StructField(
                                                      "_VALUE",
                                                      DataTypes.StringType,
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "_sequenceNumber",
                                                      DataTypes.LongType,
                                                      true,
                                                      Metadata
                                                          .empty())}),
                                          true),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:center",
                                      new ArrayType(
                                          new StructType(
                                              new StructField[]{
                                                  new StructField(
                                                      "_VALUE",
                                                      DataTypes.StringType,
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "_elapsedEntryTime",
                                                      DataTypes.LongType,
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "_sequenceNumber",
                                                      DataTypes.LongType,
                                                      true,
                                                      Metadata
                                                          .empty())}),
                                          true),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:fix",
                                      new ArrayType(
                                          new StructType(
                                              new StructField[]{
                                                  new StructField(
                                                      "_VALUE",
                                                      DataTypes.StringType,
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "_elapsedTime",
                                                      DataTypes.LongType,
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "_sequenceNumber",
                                                      DataTypes.LongType,
                                                      true,
                                                      Metadata
                                                          .empty())}),
                                          true),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:sector",
                                      new ArrayType(
                                          new StructType(
                                              new StructField[]{
                                                  new StructField(
                                                      "_VALUE",
                                                      DataTypes.StringType,
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "_elapsedEntryTime",
                                                      DataTypes.LongType,
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "_sequenceNumber",
                                                      DataTypes.LongType,
                                                      true,
                                                      Metadata
                                                          .empty())}),
                                          true),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:waypoint",
                                      new ArrayType(
                                          new StructType(
                                              new StructField[]{
                                                  new StructField(
                                                      "_VALUE",
                                                      DataTypes.StringType,
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "_elapsedTime",
                                                      DataTypes.LongType,
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "_latitudeDecimal",
                                                      DataTypes.DoubleType,
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "_longitudeDecimal",
                                                      DataTypes.DoubleType,
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "_sequenceNumber",
                                                      DataTypes.LongType,
                                                      true,
                                                      Metadata
                                                          .empty())}),
                                          true),
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:nextPosition",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_latitudeDecimal",
                                      DataTypes.DoubleType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_longitudeDecimal",
                                      DataTypes.DoubleType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:remarksKeywords",
                          DataTypes.StringType,
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:routeOfFlight",
                          DataTypes.StringType,
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:rvsmData",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_currentCompliance",
                                      DataTypes.BooleanType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_equipped",
                                      DataTypes.BooleanType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_futureCompliance",
                                      DataTypes.BooleanType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:star",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_routeName",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "_routeType",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxcm:starTransitionFix",
                          DataTypes.StringType,
                          true,
                          Metadata.empty())}),
              true, Metadata.empty()),
          new StructField(
              "nxcm:qualifiedAircraftId",
              new StructType(
                  new StructField[]{
                      new StructField(
                          "nxce:aircraftId",
                          DataTypes.StringType,
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxce:arrivalPoint",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:airport",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxce:computerId",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:facilityIdentifier",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:idNumber",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxce:departurePoint",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:airport",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:fix",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:namedFix",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxce:gufi",
                          DataTypes.StringType,
                          true,
                          Metadata.empty()),
                      new StructField(
                          "nxce:igtd",
                          DataTypes.StringType,
                          true,
                          Metadata.empty())}),
              true, Metadata.empty()),
          new StructField(
              "nxcm:routeOfFlight",
              new StructType(
                  new StructField[]{
                      new StructField(
                          "_VALUE",
                          DataTypes.StringType,
                          true,
                          Metadata.empty()),
                      new StructField(
                          "_legacyFormat",
                          DataTypes.StringType,
                          true,
                          Metadata.empty())}),
              true, Metadata.empty()),
          new StructField("nxcm:speed",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:filedTrueAirSpeed",
                                      DataTypes.LongType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:mach",
                                      DataTypes.LongType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true,
                          Metadata.empty())}),
                      true, Metadata.empty()),
      new StructField("fdm:ncsmFlightCreate", new StructType(new StructField[]{
          new StructField("nxcm:airlineData",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxcm:arrivalFixAndTime",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_arrTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_fixName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:diversionIndicator",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:eta",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_etaType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_timeValue",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:etd",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_etdType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_timeValue",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:flightStatusAndSpec",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxcm:aircraftModel",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxcm:aircraftspecification",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "_VALUE",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_aircraftEngineClass",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_equipmentQualifier",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_numberOfAircraft",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_specialAircraftQualifier",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty())}),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxcm:flightStatus",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:flightTimeData",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_airlineInTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_airlineOffTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_airlineOnTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_airlineOutTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_flightCreation",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_originalArrival",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_originalDeparture",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:rvsmData",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_currentCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_equipped",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_futureCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField(
              "nxcm:diversionCancelData",
              new StructType(new StructField[]{
                  new StructField(
                      "nxcm:canceledFlightReference",
                      DataTypes.LongType, true,
                      Metadata.empty()),
                  new StructField(
                      "nxcm:newFlightReference",
                      DataTypes.LongType, true,
                      Metadata.empty())}), true,
              Metadata.empty()),
          new StructField(
              "nxcm:qualifiedAircraftId",
              new StructType(new StructField[]{
                  new StructField(
                      "nxce:aircraftId",
                      DataTypes.StringType, true,
                      Metadata.empty()),
                  new StructField(
                      "nxce:arrivalPoint",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:airport",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField(
                      "nxce:computerId",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:facilityIdentifier",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty()),
                              new StructField(
                                  "nxce:idNumber",
                                  DataTypes.LongType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField(
                      "nxce:departurePoint",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:airport",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField("nxce:gufi",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty()),
                  new StructField("nxce:igtd",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
              true, Metadata.empty())}), true,
                      Metadata.empty()),
      new StructField("fdm:ncsmFlightModify", new StructType(new StructField[]{
          new StructField("nxcm:airlineData",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxcm:arrivalFixAndTime",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_arrTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_fixName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:diversionIndicator",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:eta",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_etaType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_timeValue",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:etd",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_etdType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_timeValue",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:flightStatusAndSpec",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxcm:aircraftModel",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxcm:aircraftspecification",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "_VALUE",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_aircraftEngineClass",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_equipmentQualifier",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_numberOfAircraft",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_specialAircraftQualifier",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty())}),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxcm:flightStatus",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:flightTimeData",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_airlineInTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_airlineOffTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_airlineOnTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_airlineOutTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_flightCreation",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_gateArrival",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_gateDeparture",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_originalArrival",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_originalDeparture",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_runwayArrival",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_runwayDeparture",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:remarksKeywords",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:rvsmData",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_currentCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_equipped",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_futureCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField(
              "nxcm:qualifiedAircraftId",
              new StructType(new StructField[]{
                  new StructField(
                      "nxce:aircraftId",
                      DataTypes.StringType, true,
                      Metadata.empty()),
                  new StructField(
                      "nxce:arrivalPoint",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:airport",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField(
                      "nxce:computerId",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:facilityIdentifier",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty()),
                              new StructField(
                                  "nxce:idNumber",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField(
                      "nxce:departurePoint",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:airport",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField("nxce:gufi",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty()),
                  new StructField("nxce:igtd",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
              true, Metadata.empty())}), true,
                      Metadata.empty()),
      new StructField("fdm:ncsmFlightRoute", new StructType(new StructField[]{
          new StructField("nxcm:altitude",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:assignedAltitude",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:simpleAltitude",
                                                  DataTypes.LongType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField(
              "nxcm:flightStatusAndSpec",
              new StructType(new StructField[]{
                  new StructField(
                      "nxcm:aircraftModel",
                      DataTypes.StringType, true,
                      Metadata.empty()),
                  new StructField(
                      "nxcm:aircraftspecification",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "_VALUE",
                                  DataTypes.StringType,
                                  true,
                                  Metadata.empty()),
                              new StructField(
                                  "_aircraftEngineClass",
                                  DataTypes.StringType,
                                  true,
                                  Metadata.empty()),
                              new StructField(
                                  "_equipmentQualifier",
                                  DataTypes.StringType,
                                  true,
                                  Metadata.empty()),
                              new StructField(
                                  "_numberOfAircraft",
                                  DataTypes.LongType,
                                  true,
                                  Metadata.empty()),
                              new StructField(
                                  "_specialAircraftQualifier",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField(
                      "nxcm:flightStatus",
                      DataTypes.StringType, true,
                      Metadata.empty())}), true,
              Metadata.empty()),
          new StructField("nxcm:ncsmRouteData",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxcm:arrivalFixAndTime",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_arrTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_fixName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:departureFixAndTime",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_arrTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_fixName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:diversionIndicator",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:dp",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_routeName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_routeType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:dpTransitionFix",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:eta",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_etaType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_timeValue",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:etd",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_etdType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_timeValue",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:flightTraversalData2",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:airway",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:center",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_elapsedEntryTime",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:fix",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_elapsedTime",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:sector",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_elapsedEntryTime",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:waypoint",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_elapsedTime",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_latitudeDecimal",
                                                                  DataTypes.DoubleType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_longitudeDecimal",
                                                                  DataTypes.DoubleType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:nextPosition",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_latitudeDecimal",
                                                  DataTypes.DoubleType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_longitudeDecimal",
                                                  DataTypes.DoubleType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:remarksKeywords",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:routeOfFlight",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:rvsmData",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_currentCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_equipped",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_futureCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:star",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_routeName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_routeType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:starTransitionFix",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField(
              "nxcm:qualifiedAircraftId",
              new StructType(new StructField[]{
                  new StructField("nxce:aircraftId",
                                  DataTypes.StringType,
                                  true,
                                  Metadata.empty()),
                  new StructField(
                      "nxce:arrivalPoint",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:airport",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField("nxce:computerId",
                                  new StructType(
                                      new StructField[]{
                                          new StructField(
                                              "nxce:facilityIdentifier",
                                              DataTypes.StringType,
                                              true,
                                              Metadata
                                                  .empty()),
                                          new StructField(
                                              "nxce:idNumber",
                                              DataTypes.LongType,
                                              true,
                                              Metadata
                                                  .empty())}),
                                  true,
                                  Metadata.empty()),
                  new StructField(
                      "nxce:departurePoint",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:airport",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField("nxce:gufi",
                                  DataTypes.StringType,
                                  true,
                                  Metadata.empty()),
                  new StructField("nxce:igtd",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
              true, Metadata.empty()),
          new StructField("nxcm:speed",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:filedTrueAirSpeed",
                                      DataTypes.LongType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:groundSpeed",
                                      DataTypes.LongType,
                                      true, Metadata
                                          .empty())}),
                          true, Metadata.empty())}),
                      true, Metadata.empty()),
      new StructField("fdm:ncsmFlightScheduleActivate", new StructType(new StructField[]{
          new StructField("nxcm:altitude", new StructType(new StructField[]{new StructField("nxce:assignedAltitude",
                                                                                            new StructType(
                                                                                                new StructField[]{
                                                                                                    new StructField(
                                                                                                        "nxce:simpleAltitude",
                                                                                                        DataTypes.LongType,
                                                                                                        true, Metadata
                                                                                                            .empty())}),
                                                                                            true, Metadata.empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:flightStatusAndSpec", new StructType(
              new StructField[]{new StructField("nxcm:aircraftModel", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("nxcm:aircraftspecification", new StructType(new StructField[]{
                                    new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_aircraftEngineClass", DataTypes.StringType, true,
                                                    Metadata.empty()),
                                    new StructField("_numberOfAircraft", DataTypes.LongType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxcm:flightStatus", DataTypes.StringType, true, Metadata.empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:ncsmRouteData", new StructType(new StructField[]{
              new StructField("nxcm:arrivalFixAndTime", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_arrTime", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_fixName", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:departureFixAndTime", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_arrTime", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_fixName", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:diversionIndicator", DataTypes.StringType, true, Metadata.empty()),
              new StructField("nxcm:dp", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_routeName", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_routeType", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:dpTransitionFix", DataTypes.StringType, true, Metadata.empty()),
              new StructField("nxcm:eta", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_etaType", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_timeValue", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:etd", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_etdType", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_timeValue", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:flightTraversalData2", new StructType(new StructField[]{
                  new StructField("nxce:airway", new ArrayType(new StructType(
                      new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                        new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                        Metadata.empty())}), true),
                                  true, Metadata.empty()),
                  new StructField("nxce:center", new ArrayType(new StructType(
                      new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                        new StructField("_elapsedEntryTime", DataTypes.LongType, true,
                                                        Metadata.empty()),
                                        new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                        Metadata.empty())}), true),
                                  true, Metadata.empty()),
                  new StructField("nxce:fix", new ArrayType(new StructType(
                      new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                        new StructField("_elapsedTime", DataTypes.LongType, true, Metadata.empty()),
                                        new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                        Metadata.empty())}), true),
                                  true, Metadata.empty()),
                  new StructField("nxce:sector", new ArrayType(new StructType(
                      new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                        new StructField("_elapsedEntryTime", DataTypes.LongType, true,
                                                        Metadata.empty()),
                                        new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                        Metadata.empty())}), true),
                                  true, Metadata.empty()),
                  new StructField("nxce:waypoint", new ArrayType(new StructType(
                      new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                        new StructField("_elapsedTime", DataTypes.LongType, true, Metadata.empty()),
                                        new StructField("_latitudeDecimal", DataTypes.DoubleType, true,
                                                        Metadata.empty()),
                                        new StructField("_longitudeDecimal", DataTypes.DoubleType, true,
                                                        Metadata.empty()),
                                        new StructField("_sequenceNumber", DataTypes.LongType, true,
                                                        Metadata.empty())}), true),
                                  true, Metadata.empty())}), true, Metadata.empty()),
              new StructField("nxcm:nextPosition", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_latitudeDecimal", DataTypes.DoubleType, true, Metadata.empty()),
                                    new StructField("_longitudeDecimal", DataTypes.DoubleType, true,
                                                    Metadata.empty())}), true, Metadata.empty()),
              new StructField("nxcm:routeOfFlight", DataTypes.StringType, true, Metadata.empty()),
              new StructField("nxcm:rvsmData", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_currentCompliance", DataTypes.BooleanType, true,
                                                    Metadata.empty()),
                                    new StructField("_equipped", DataTypes.BooleanType, true, Metadata.empty()),
                                    new StructField("_futureCompliance", DataTypes.BooleanType, true,
                                                    Metadata.empty())}), true, Metadata.empty()),
              new StructField("nxcm:star", new StructType(
                  new StructField[]{new StructField("_VALUE", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_routeName", DataTypes.StringType, true, Metadata.empty()),
                                    new StructField("_routeType", DataTypes.StringType, true, Metadata.empty())}), true,
                              Metadata.empty()),
              new StructField("nxcm:starTransitionFix", DataTypes.StringType, true, Metadata.empty())}), true,
                          Metadata.empty()),
          new StructField("nxcm:qualifiedAircraftId", new StructType(
              new StructField[]{new StructField("nxce:aircraftId", DataTypes.StringType, true, Metadata.empty()),
                                new StructField("nxce:arrivalPoint", new StructType(new StructField[]{
                                    new StructField("nxce:airport", DataTypes.StringType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:computerId", new StructType(new StructField[]{
                                    new StructField("nxce:facilityIdentifier", DataTypes.StringType, true,
                                                    Metadata.empty())}), true, Metadata.empty()),
                                new StructField("nxce:departurePoint", new StructType(new StructField[]{
                                    new StructField("nxce:airport", DataTypes.StringType, true, Metadata.empty())}),
                                                true, Metadata.empty()),
                                new StructField("nxce:igtd", DataTypes.StringType, true, Metadata.empty())}), true,
                          Metadata.empty()),
          new StructField("nxcm:speed", new StructType(new StructField[]{
              new StructField("nxce:filedTrueAirSpeed", DataTypes.LongType, true, Metadata.empty())}),
                          true, Metadata.empty())}), true, Metadata.empty()),
      new StructField("fdm:ncsmFlightTimes", new StructType(new StructField[]{
          new StructField("nxcm:arrivalFixAndTime",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "_arrTime",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "_fixName",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:cta",
                          DataTypes.StringType,
                          true, Metadata.empty()),
          new StructField("nxcm:ctd",
                          DataTypes.StringType,
                          true, Metadata.empty()),
          new StructField(
              "nxcm:departureFixAndTime",
              new StructType(new StructField[]{
                  new StructField("_VALUE",
                                  DataTypes.StringType,
                                  true,
                                  Metadata.empty()),
                  new StructField("_arrTime",
                                  DataTypes.StringType,
                                  true,
                                  Metadata.empty()),
                  new StructField("_fixName",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
              true, Metadata.empty()),
          new StructField("nxcm:eta",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "_etaType",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "_timeValue",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:etd",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "_etdType",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "_timeValue",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField(
              "nxcm:flightStatusAndSpec",
              new StructType(new StructField[]{
                  new StructField(
                      "nxcm:aircraftModel",
                      DataTypes.StringType, true,
                      Metadata.empty()),
                  new StructField(
                      "nxcm:aircraftspecification",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "_VALUE",
                                  DataTypes.StringType,
                                  true,
                                  Metadata.empty()),
                              new StructField(
                                  "_aircraftEngineClass",
                                  DataTypes.StringType,
                                  true,
                                  Metadata.empty()),
                              new StructField(
                                  "_equipmentQualifier",
                                  DataTypes.StringType,
                                  true,
                                  Metadata.empty()),
                              new StructField(
                                  "_numberOfAircraft",
                                  DataTypes.LongType,
                                  true,
                                  Metadata.empty()),
                              new StructField(
                                  "_specialAircraftQualifier",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField(
                      "nxcm:flightStatus",
                      DataTypes.StringType, true,
                      Metadata.empty())}), true,
              Metadata.empty()),
          new StructField(
              "nxcm:qualifiedAircraftId",
              new StructType(new StructField[]{
                  new StructField("nxce:aircraftId",
                                  DataTypes.StringType,
                                  true,
                                  Metadata.empty()),
                  new StructField(
                      "nxce:arrivalPoint",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:airport",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField("nxce:computerId",
                                  new StructType(
                                      new StructField[]{
                                          new StructField(
                                              "nxce:facilityIdentifier",
                                              DataTypes.StringType,
                                              true,
                                              Metadata
                                                  .empty()),
                                          new StructField(
                                              "nxce:idNumber",
                                              DataTypes.StringType,
                                              true,
                                              Metadata
                                                  .empty())}),
                                  true,
                                  Metadata.empty()),
                  new StructField(
                      "nxce:departurePoint",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:airport",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField("nxce:gufi",
                                  DataTypes.StringType,
                                  true,
                                  Metadata.empty()),
                  new StructField("nxce:igtd",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
              true, Metadata.empty()),
          new StructField("nxcm:rvsmData",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "_VALUE",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "_currentCompliance",
                                      DataTypes.BooleanType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "_equipped",
                                      DataTypes.BooleanType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "_futureCompliance",
                                      DataTypes.BooleanType,
                                      true, Metadata
                                          .empty())}),
                          true, Metadata.empty())}),
                      true, Metadata.empty()),
      new StructField("fdm:oceanicReport", new StructType(new StructField[]{
          new StructField("nxcm:ncsmRouteData",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxcm:arrivalFixAndTime",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_arrTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_fixName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:departureFixAndTime",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_arrTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_fixName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:diversionIndicator",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:dp",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_routeName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_routeType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:dpTransitionFix",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:eta",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_etaType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_timeValue",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:etd",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_etdType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_timeValue",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:flightTraversalData2",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:airway",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:center",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_elapsedEntryTime",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:fix",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_elapsedTime",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:sector",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_elapsedEntryTime",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:waypoint",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_elapsedTime",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_latitudeDecimal",
                                                                  DataTypes.DoubleType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_longitudeDecimal",
                                                                  DataTypes.DoubleType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:nextPosition",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_latitudeDecimal",
                                                  DataTypes.DoubleType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_longitudeDecimal",
                                                  DataTypes.DoubleType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:remarksKeywords",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:routeOfFlight",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:rvsmData",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_currentCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_equipped",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_futureCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:star",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_routeName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_routeType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:starTransitionFix",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:ncsmTrackData",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxcm:arrivalFixAndTime",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_arrTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_fixName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:departureFixAndTime",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_arrTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_fixName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:eta",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_etaType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_timeValue",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:nextEvent",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_latitudeDecimal",
                                                  DataTypes.DoubleType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_longitudeDecimal",
                                                  DataTypes.DoubleType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:rvsmData",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_currentCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_equipped",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_futureCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:plannedPositionData",
                          new ArrayType(
                              new StructType(
                                  new StructField[]{
                                      new StructField(
                                          "_planNumber",
                                          DataTypes.LongType,
                                          true,
                                          Metadata
                                              .empty()),
                                      new StructField(
                                          "nxce:altitude",
                                          DataTypes.LongType,
                                          true,
                                          Metadata
                                              .empty()),
                                      new StructField(
                                          "nxce:position",
                                          new StructType(
                                              new StructField[]{
                                                  new StructField(
                                                      "nxce:latitude",
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "nxce:latitudeDMS",
                                                                  new StructType(
                                                                      new StructField[]{
                                                                          new StructField(
                                                                              "_VALUE",
                                                                              DataTypes.StringType,
                                                                              true,
                                                                              Metadata
                                                                                  .empty()),
                                                                          new StructField(
                                                                              "_degrees",
                                                                              DataTypes.LongType,
                                                                              true,
                                                                              Metadata
                                                                                  .empty()),
                                                                          new StructField(
                                                                              "_direction",
                                                                              DataTypes.StringType,
                                                                              true,
                                                                              Metadata
                                                                                  .empty()),
                                                                          new StructField(
                                                                              "_minutes",
                                                                              DataTypes.LongType,
                                                                              true,
                                                                              Metadata
                                                                                  .empty()),
                                                                          new StructField(
                                                                              "_seconds",
                                                                              DataTypes.LongType,
                                                                              true,
                                                                              Metadata
                                                                                  .empty())
                                                                      }),
                                                                  true,
                                                                  Metadata
                                                                      .empty())
                                                          }),
                                                      true,
                                                      Metadata
                                                          .empty()),
                                                  new StructField(
                                                      "nxce:longitude",
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "nxce:longitudeDMS",
                                                                  new StructType(
                                                                      new StructField[]{
                                                                          new StructField(
                                                                              "_VALUE",
                                                                              DataTypes.StringType,
                                                                              true,
                                                                              Metadata
                                                                                  .empty()),
                                                                          new StructField(
                                                                              "_degrees",
                                                                              DataTypes.LongType,
                                                                              true,
                                                                              Metadata
                                                                                  .empty()),
                                                                          new StructField(
                                                                              "_direction",
                                                                              DataTypes.StringType,
                                                                              true,
                                                                              Metadata
                                                                                  .empty()),
                                                                          new StructField(
                                                                              "_minutes",
                                                                              DataTypes.LongType,
                                                                              true,
                                                                              Metadata
                                                                                  .empty()),
                                                                          new StructField(
                                                                              "_seconds",
                                                                              DataTypes.LongType,
                                                                              true,
                                                                              Metadata
                                                                                  .empty())
                                                                      }),
                                                                  true,
                                                                  Metadata
                                                                      .empty())
                                                          }),
                                                      true,
                                                      Metadata
                                                          .empty())
                                              }),
                                          true,
                                          Metadata
                                              .empty()),
                                      new StructField(
                                          "nxce:time",
                                          DataTypes.StringType,
                                          true,
                                          Metadata
                                              .empty())}),
                              true), true,
                          Metadata.empty()),
          new StructField("nxcm:qualifiedAircraftId",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:aircraftId",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:arrivalPoint",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:airport",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata.empty())}),
                                      true, Metadata.empty()),
                                  new StructField(
                                      "nxce:computerId",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:facilityIdentifier",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata.empty()),
                                              new StructField(
                                                  "nxce:idNumber",
                                                  DataTypes.LongType,
                                                  true,
                                                  Metadata.empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:departurePoint",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:airport",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:gufi",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:igtd",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:reportedPositionData",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:altitude",
                                      DataTypes.LongType,
                                      true, Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:position",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:latitude",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "nxce:latitudeDMS",
                                                              new StructType(
                                                                  new StructField[]{
                                                                      new StructField(
                                                                          "_VALUE",
                                                                          DataTypes.StringType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_degrees",
                                                                          DataTypes.LongType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_direction",
                                                                          DataTypes.StringType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_minutes",
                                                                          DataTypes.LongType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_seconds",
                                                                          DataTypes.LongType,
                                                                          true,
                                                                          Metadata
                                                                              .empty())
                                                                  }),
                                                              true,
                                                              Metadata
                                                                  .empty())
                                                      }),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:longitude",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "nxce:longitudeDMS",
                                                              new StructType(
                                                                  new StructField[]{
                                                                      new StructField(
                                                                          "_VALUE",
                                                                          DataTypes.StringType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_degrees",
                                                                          DataTypes.LongType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_direction",
                                                                          DataTypes.StringType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_minutes",
                                                                          DataTypes.LongType,
                                                                          true,
                                                                          Metadata
                                                                              .empty()),
                                                                      new StructField(
                                                                          "_seconds",
                                                                          DataTypes.LongType,
                                                                          true,
                                                                          Metadata
                                                                              .empty())
                                                                  }),
                                                              true,
                                                              Metadata
                                                                  .empty())
                                                      }),
                                                  true,
                                                  Metadata
                                                      .empty())
                                          }), true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:time",
                                      DataTypes.StringType,
                                      true, Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:speed",
                          DataTypes.LongType, true,
                          Metadata.empty())}), true,
                      Metadata.empty()),
      new StructField("fdm:trackInformation", new StructType(new StructField[]{
          new StructField("nxcm:ncsmRouteData",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxcm:arrivalFixAndTime",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_arrTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_fixName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:departureFixAndTime",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_arrTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_fixName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:diversionIndicator",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:dp",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_routeName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_routeType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:dpTransitionFix",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:eta",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_etaType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_timeValue",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:etd",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_etdType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_timeValue",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:flightTraversalData2",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:airway",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:center",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_elapsedEntryTime",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:fix",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_elapsedTime",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:sector",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_elapsedEntryTime",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:waypoint",
                                                  new ArrayType(
                                                      new StructType(
                                                          new StructField[]{
                                                              new StructField(
                                                                  "_VALUE",
                                                                  DataTypes.StringType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_elapsedTime",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_latitudeDecimal",
                                                                  DataTypes.DoubleType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_longitudeDecimal",
                                                                  DataTypes.DoubleType,
                                                                  true,
                                                                  Metadata
                                                                      .empty()),
                                                              new StructField(
                                                                  "_sequenceNumber",
                                                                  DataTypes.LongType,
                                                                  true,
                                                                  Metadata
                                                                      .empty())}),
                                                      true),
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:nextPosition",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_latitudeDecimal",
                                                  DataTypes.DoubleType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_longitudeDecimal",
                                                  DataTypes.DoubleType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:remarksKeywords",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:routeOfFlight",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:rvsmData",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_currentCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_equipped",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_futureCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:star",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_routeName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_routeType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:starTransitionFix",
                                      DataTypes.StringType,
                                      true,
                                      Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:ncsmTrackData",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxcm:arrivalFixAndTime",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_arrTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_fixName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:departureFixAndTime",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_arrTime",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_fixName",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:eta",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_etaType",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_timeValue",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:nextEvent",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_latitudeDecimal",
                                                  DataTypes.DoubleType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_longitudeDecimal",
                                                  DataTypes.DoubleType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxcm:rvsmData",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "_VALUE",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_currentCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_equipped",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "_futureCompliance",
                                                  DataTypes.BooleanType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:position",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:latitude",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:latitudeDMS",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "_VALUE",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_degrees",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_direction",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_minutes",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_seconds",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty())
                                                      }),
                                                  true,
                                                  Metadata
                                                      .empty())
                                          }), true,
                                      Metadata
                                          .empty()),
                                  new StructField(
                                      "nxce:longitude",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:longitudeDMS",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "_VALUE",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_degrees",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_direction",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_minutes",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_seconds",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty())
                                                      }),
                                                  true,
                                                  Metadata
                                                      .empty())
                                          }), true,
                                      Metadata
                                          .empty())
                              }), true,
                          Metadata.empty()),
          new StructField(
              "nxcm:qualifiedAircraftId",
              new StructType(new StructField[]{
                  new StructField(
                      "nxce:aircraftId",
                      DataTypes.StringType, true,
                      Metadata.empty()),
                  new StructField(
                      "nxce:arrivalPoint",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:airport",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField(
                      "nxce:computerId",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:facilityIdentifier",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty()),
                              new StructField(
                                  "nxce:idNumber",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField(
                      "nxce:departurePoint",
                      new StructType(
                          new StructField[]{
                              new StructField(
                                  "nxce:airport",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
                      true, Metadata.empty()),
                  new StructField("nxce:gufi",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty()),
                  new StructField("nxce:igtd",
                                  DataTypes.StringType,
                                  true, Metadata
                                      .empty())}),
              true, Metadata.empty()),
          new StructField("nxcm:reportedAltitude",
                          new StructType(
                              new StructField[]{
                                  new StructField(
                                      "nxce:assignedAltitude",
                                      new StructType(
                                          new StructField[]{
                                              new StructField(
                                                  "nxce:blockedAltitude",
                                                  new StructType(
                                                      new StructField[]{
                                                          new StructField(
                                                              "_VALUE",
                                                              DataTypes.StringType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_max",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty()),
                                                          new StructField(
                                                              "_min",
                                                              DataTypes.LongType,
                                                              true,
                                                              Metadata
                                                                  .empty())}),
                                                  true,
                                                  Metadata
                                                      .empty()),
                                              new StructField(
                                                  "nxce:simpleAltitude",
                                                  DataTypes.StringType,
                                                  true,
                                                  Metadata
                                                      .empty())}),
                                      true,
                                      Metadata
                                          .empty())}),
                          true, Metadata.empty()),
          new StructField("nxcm:speed",
                          DataTypes.LongType, true,
                          Metadata.empty()),
          new StructField("nxcm:timeAtPosition",
                          DataTypes.StringType,
                          true,
                          Metadata.empty())}),
                      true, Metadata.empty())});

  public static final StructType getSchema() {
    return structType;
  }
}
