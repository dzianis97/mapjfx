/*
 Copyright 2015-2020 Peter-Josef Meisch (pj.meisch@sothawo.com)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.sothawo.mapjfx.app;

import com.sothawo.mapjfx.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Test application.
 *
 * @author P.J. Meisch (pj.meisch@sothawo.com).
 */
public class TestApp2 extends Application {

    private static final Logger logger = LoggerFactory.getLogger(TestApp2.class);

    private static final Coordinate DEFAULT_COORDINATE = new Coordinate(53.9284391, 27.5177054);
    /**
     * some coordinates from around town
     */
    private static final Coordinate coordKarlsruheCastle = new Coordinate(49.013517, 8.404435);
    private static final Coordinate coordKarlsruheHarbour = new Coordinate(49.015511, 8.323497);
    private static final Coordinate coordKarlsruheStation = new Coordinate(48.993284, 8.402186);
    private static final Extent extentAll =
            Extent.forCoordinates(coordKarlsruheHarbour, coordKarlsruheCastle, coordKarlsruheStation);

    private static final CoordinateLine coordinateLine =
            new CoordinateLine(coordKarlsruheCastle, coordKarlsruheHarbour, coordKarlsruheStation)
                    .setVisible(true)
                    .setColor(Color.DODGERBLUE)
                    .setWidth(7)
                    .setClosed(true)
                    .setFillColor(Color.web("lawngreen", 0.5));

    private static final Marker marker;

    private static final MapLabel mapLabel;

    //private static final WMSParam wmsParam;


    static {
        marker = Marker.createProvided(Marker.Provided.BLUE).setPosition(coordKarlsruheCastle).setVisible(true);
        marker.setRotateWithMap(true);
        mapLabel = new MapLabel("blau!")
                .setCssClass("blue-label")
                .setPosition(coordKarlsruheCastle)
                .setVisible(true);

        marker.attachLabel(mapLabel);

//        wmsParam = new WMSParam()
//                .setUrl("http://irs.gis-lab.info/?")
//                .addParam("layers", "landsat")
//                .addParam("REQUEST", "GetTile");

//        wmsParam = new WMSParam()
//                .setUrl("http://geonode.wfp.org:80/geoserver/ows")
//                .addParam("layers", "geonode:admin_2_gaul_2015");

//        wmsParam = new WMSParam()
//                .setUrl("http://ows.terrestris.de/osm/service")
//                .addParam("layers", "OSM-WMS");


    }

    /**
     * the MapView
     */
    private MapView mapView;

    /**
     * api keys for bing maps.
     */
    private TextField bingApiKey;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        logger.info("starting devtest program...");
        final BorderPane borderPane = new BorderPane();

        // MapView in the center with an initial coordinate (optional)
        // the MapView is created first as the other elements reference it
        mapView = new MapView();
        // animate pan and zoom with 500ms
        //mapView.setAnimationDuration(500);
        borderPane.setCenter(mapView);

        // at the top some buttons
        final Pane topPane = createTopPane();
        borderPane.setTop(topPane);

        // at the bottom some infos
        borderPane.setBottom(createBottomPane());

        // add WMSParam
        //mapView.setWMSParam(wmsParam);

        // set custom css url
        mapView.setCustomMapviewCssURL(getClass().getResource("/custom_mapview.css"));

        // now initialize the mapView
//        mapView.initialize();
        var xyzParams = new XYZParam()
                .withUrl("https://tile.openstreetmap.org/{z}/{x}/{y}.png")
                .withAttributions("'Tiles &copy; <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>'");
        mapView.setXYZParam(xyzParams);
        mapView.setMapType(MapType.XYZ);
        mapView.setZoom(14.0);
        mapView.setRotation(0.0);
        //mapView.setCustomMapviewCssURL(getClass().getResource("/css/map.css"));
        mapView.initialize(Configuration.builder()
                .projection(Projection.WEB_MERCATOR)
                .showZoomControls(true)
                .interactive(true)
                .build());
        mapView.setCenter(DEFAULT_COORDINATE);

        // show the whole thing
        final Scene scene = new Scene(borderPane, 1200, 800);

        primaryStage.setTitle("sothawo mapjfx devtest program");
        primaryStage.setScene(scene);
        primaryStage.show();

        logger.debug("application started.");
    }

    @Override
    public void stop() throws Exception {
        mapView.close();
        super.stop();
    }

    private void initOfflineCache() {
//        final OfflineCache offlineCache = OfflineCache.INSTANCE;
//        offlineCache.setCacheDirectory(FileSystems.getDefault().getPath("tmpdata/cache"));
//        offlineCache.setActive(true);
//        offlineCache.setNoCacheFilters(Collections.singletonList(".*\\.sothawo\\.com/.*"));

        LinkedList<String> urls = new LinkedList<>();
        urls.add("https://c.tile.openstreetmap.org/14/8572/5626.png");
        urls.add("https://b.tile.openstreetmap.org/14/8571/5626.png");
        urls.add("https://a.tile.openstreetmap.org/14/8572/5625.png");
        urls.add("https://c.tile.openstreetmap.org/14/8571/5625.png");
        urls.add("https://b.tile.openstreetmap.org/14/8570/5625.png");
        urls.add("https://a.tile.openstreetmap.org/14/8572/5625.png");
        urls.add("https://a.tile.openstreetmap.org/14/8570/5626.png");
        urls.add("https://a.tile.openstreetmap.org/14/8571/5627.png");
        urls.add("https://a.tile.openstreetmap.org/14/8573/5626.png");
        urls.add("https://a.tile.openstreetmap.org/14/8574/5627.png");
        urls.add("https://b.tile.openstreetmap.org/14/8571/5626.png");
        urls.add("https://b.tile.openstreetmap.org/14/8573/5625.png");
        urls.add("https://b.tile.openstreetmap.org/14/8572/5627.png");
        urls.add("https://b.tile.openstreetmap.org/14/8574/5626.png");
        urls.add("https://c.tile.openstreetmap.org/14/8572/5626.png");
        urls.add("https://c.tile.openstreetmap.org/14/8570/5627.png");
        urls.add("https://c.tile.openstreetmap.org/14/8574/5625.png");
        urls.add("https://c.tile.openstreetmap.org/14/8573/5627.png");

        //offlineCache.preloadURLs(urls, 2);
    }

    /**
     * creates the bottom pane with status labels.
     *
     * @return Pane
     */
    private Pane createBottomPane() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(10);

        // label for showing the map's center
        Label labelCenter = new Label();
        hbox.getChildren().add(labelCenter);
        // add an observer for the map's center property to adjust the corresponding label
        mapView.centerProperty().addListener((observable, oldValue, newValue) -> {
            labelCenter.setText(newValue == null ? "" : ("center: " + newValue.toString()));
        });

        // label for showing the map's zoom
        Label labelZoom = new Label();
        hbox.getChildren().add(labelZoom);
        // add an observer to adjust the label
        mapView.zoomProperty().addListener((observable, oldValue, newValue) -> {
            labelZoom.setText(null == newValue ? "" : ("zoom: " + newValue.toString()));
        });

        // label for showing the map's zoom
        Label labelRotation = new Label();
        hbox.getChildren().add(labelRotation);
        // add an observer to adjust the label
        mapView.rotationProperty().addListener((observable, oldValue, newValue) -> {
            labelRotation.setText(null == newValue ? "" : ("rotation: " + newValue.toString()));
        });
        return hbox;
    }

    /**
     * creates the top pane with the different location buttons.
     *
     * @return Pane
     */
    private Pane createTopPane() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(5, 5, 5, 5));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(5);
        vbox.getChildren().add(hbox);

        Button btn = new Button();
        btn.setText("Karlsruhe castle");
        btn.setOnAction(event -> mapView.setCenter(coordKarlsruheCastle));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("Karlsruhe harbour");
        btn.setOnAction(event -> mapView.setCenter(coordKarlsruheHarbour));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("Karlsruhe station");
        btn.setOnAction(event -> mapView.setCenter(coordKarlsruheStation));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("all");
        btn.setOnAction(event -> mapView.setExtent(extentAll));
        hbox.getChildren().add(btn);

        Slider slider = new Slider(MapView.MIN_ZOOM, MapView.MAX_ZOOM, MapView.INITIAL_ZOOM);
        slider.setBlockIncrement(1);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickUnit(MapView.MAX_ZOOM / 4);
        slider.setMinorTickCount((MapView.MAX_ZOOM / 4) - 1);
        slider.valueProperty().bindBidirectional(mapView.zoomProperty());
        slider.setSnapToTicks(true);
        HBox.setHgrow(slider, Priority.ALWAYS);
        hbox.getChildren().add(slider);

        hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(5);
        vbox.getChildren().add(hbox);

        btn = new Button();
        btn.setText("OSM");
        btn.setOnAction(evt -> mapView.setMapType(MapType.OSM));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("ST");
        btn.setOnAction(evt -> mapView.setMapType(MapType.STAMEN_WC));
        hbox.getChildren().add(btn);

        LinkedList<MenuItem> bingOptions = new LinkedList<>();
        MenuItem item = new MenuItem("Bing Roads");
        item.setOnAction(evt -> {
            mapView.setBingMapsApiKey(bingApiKey.getText());
            mapView.setMapType(MapType.BINGMAPS_ROAD);
        });
        bingOptions.add(item);

        item = new MenuItem("Bing Aerial");
        item.setOnAction(evt -> {
            mapView.setBingMapsApiKey(bingApiKey.getText());
            mapView.setMapType(MapType.BINGMAPS_AERIAL);
        });
        bingOptions.add(item);

        item = new MenuItem("Bing Aerial with Labels");
        item.setOnAction(evt -> {
            mapView.setBingMapsApiKey(bingApiKey.getText());
            mapView.setMapType(MapType.BINGMAPS_AERIAL_WITH_LABELS);
        });
        bingOptions.add(item);

        item = new MenuItem("Bing Roads - dark");
        item.setOnAction(evt -> {
            mapView.setBingMapsApiKey(bingApiKey.getText());
            mapView.setMapType(MapType.BINGMAPS_CANVAS_DARK);
        });
        bingOptions.add(item);

        item = new MenuItem("Bing Roads - grayscale");
        item.setOnAction(evt -> {
            mapView.setBingMapsApiKey(bingApiKey.getText());
            mapView.setMapType(MapType.BINGMAPS_CANVAS_GRAY);
        });
        bingOptions.add(item);

        item = new MenuItem("Bing Roads - light");
        item.setOnAction(evt -> {
            mapView.setBingMapsApiKey(bingApiKey.getText());
            mapView.setMapType(MapType.BINGMAPS_CANVAS_LIGHT);
        });
        bingOptions.add(item);

        btn = new Button();
        btn.setText("Set Rotation");
        btn.setOnAction(evt -> mapView.setRotation(Math.PI / 6));
        hbox.getChildren().add(btn);


        MenuButton menuButton = new MenuButton("Bing", null, bingOptions.toArray(new MenuItem[0]));
        hbox.getChildren().add(menuButton);


        btn = new Button();
        btn.setText("WMS");
        btn.setOnAction(evt -> mapView.setMapType(MapType.WMS));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("XYZ");
        btn.setOnAction(evt -> mapView.setMapType(MapType.XYZ));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("add marker");
        btn.setOnAction(evt -> mapView.addMarker(marker));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("toggle marker visibility");
        btn.setOnAction(evt -> marker.setVisible(!marker.getVisible()));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("rotate marker right");
        btn.setOnAction(evt -> marker.setRotation(marker.getRotation() + 1));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("rotate marker left");
        btn.setOnAction(evt -> marker.setRotation(marker.getRotation() - 1));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("remove marker");
        btn.setOnAction(evt -> mapView.removeMarker(marker));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("add Track");
        btn.setOnAction(evt -> mapView.addCoordinateLine(coordinateLine));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("remove Track");
        btn.setOnAction(evt -> mapView.removeCoordinateLine(coordinateLine));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("toggle Track visibilty");
        btn.setOnAction(evt -> coordinateLine.setVisible(!coordinateLine.getVisible()));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("GC");
        btn.setOnAction(evt -> {
            System.gc();
        });
        hbox.getChildren().add(btn);

        hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(5);
        hbox.getChildren().add(new Label("Bing Maps API Key:"));
        bingApiKey = new TextField();
        hbox.getChildren().add(bingApiKey);

        btn = new Button();
        btn.setText("add label");
        btn.setOnAction(evt -> mapView.addLabel(mapLabel));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("toggle label visibility");
        btn.setOnAction(evt -> mapLabel.setVisible(!mapLabel.getVisible()));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("remove label");
        btn.setOnAction(evt -> mapView.removeLabel(mapLabel));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("clear cache");
        btn.setOnAction(evt -> {
            try {
                mapView.getOfflineCache().clear();
            } catch (IOException e) {
                logger.warn("could not clear cache", e);
            }
        });
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("set constrain");
        btn.setOnAction(evt -> mapView.constrainExtent(extentAll));
        hbox.getChildren().add(btn);

        btn = new Button();
        btn.setText("clear constrain");
        btn.setOnAction(evt -> mapView.clearConstrainExtent());
        hbox.getChildren().add(btn);

        vbox.getChildren().add(hbox);

        vbox.setDisable(true);

        return vbox;
    }
}
