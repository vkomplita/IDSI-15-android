package ch.unige.idsi15.swisstoolapp.model;

import ch.unige.idsi15.swisstoolapp.model.Placemark;
import ch.unige.idsi15.swisstoolapp.model.Point;
import ch.unige.idsi15.swisstoolapp.model.Placemark.PlacemarkBuilder;

public class Placemark {

    private String name;
    private String description;
    private Point point;

    public Placemark() {

    }

    public Placemark(String name, Point point, String description) {
        this.name = name;
        this.point = point;
        this.description = description;
    }


    public static class PlacemarkBuilder {
        private String name;
        private Point point;
        private String description;

        public PlacemarkBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PlacemarkBuilder setPoint(Point point) {
            this.point = point;
            return this;
        }

        public PlacemarkBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Placemark createPlacemark() {
            return new Placemark(name, point, description);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

