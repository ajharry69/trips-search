package com.hava.trips.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Locale.ROOT;

@Entity(tableName = "trips")
public class Trip {

    @PrimaryKey
    private Long id;
    private Status status;
    private String requestDate, pickupLocation, dropoffLocation, pickupDate, dropoffDate,
            type, driverName, driverPic, carMake, carModel, carNumber, carPic, durationUnit,
            distanceUnit, costUnit;
    private Double pickupLat, pickupLng, dropoffLat, dropoffLng;
    private int driverId, driverRating, carYear, duration;
    private float distance, cost;

    public enum Status {
        COMPLETED,
        CANCELLED
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public Double getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(Double pickupLat) {
        this.pickupLat = pickupLat;
    }

    public Double getPickupLng() {
        return pickupLng;
    }

    public void setPickupLng(Double pickupLng) {
        this.pickupLng = pickupLng;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Double getDropoffLat() {
        return dropoffLat;
    }

    public void setDropoffLat(Double dropoffLat) {
        this.dropoffLat = dropoffLat;
    }

    public Double getDropoffLng() {
        return dropoffLng;
    }

    public void setDropoffLng(Double dropoffLng) {
        this.dropoffLng = dropoffLng;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getDropoffDate() {
        return dropoffDate;
    }

    public void setDropoffDate(String dropoffDate) {
        this.dropoffDate = dropoffDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(int driverRating) {
        this.driverRating = driverRating;
    }

    public String getDriverPic() {
        return driverPic;
    }

    public void setDriverPic(String driverPic) {
        this.driverPic = driverPic;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public int getCarYear() {
        return carYear;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public String getCarPic() {
        return carPic;
    }

    public void setCarPic(String carPic) {
        this.carPic = carPic;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getCostUnit() {
        return costUnit;
    }

    public void setCostUnit(String costUnit) {
        this.costUnit = costUnit;
    }

    public Set<String> ftsSearchable() {
        Set<String> queries = new HashSet<>();
        queries.add((getPickupLocation() == null ? "" : getPickupLocation()).toLowerCase(ROOT));
        queries.add((getDropoffLocation() == null ? "" : getDropoffLocation()).toLowerCase(ROOT));
        queries.add((getType() == null ? "" : getType()).toLowerCase(ROOT));
        queries.add((getDriverName() == null ? "" : getDriverName()).toLowerCase(ROOT));
        queries.add((getCarMake() == null ? "" : getCarMake()).toLowerCase(ROOT));
        queries.add((getCarModel() == null ? "" : getCarModel()).toLowerCase(ROOT));
        queries.add((getCarNumber() == null ? "" : getCarNumber()).toLowerCase(ROOT));
        return queries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip)) return false;

        Trip trip = (Trip) o;

        if (getId() != null ? !getId().equals(trip.getId()) : trip.getId() != null) return false;
        if (getDriverId() != trip.getDriverId()) return false;
        if (getDriverRating() != trip.getDriverRating()) return false;
        if (getCarYear() != trip.getCarYear()) return false;
        if (getDuration() != trip.getDuration()) return false;
        if (Float.compare(trip.getDistance(), getDistance()) != 0) return false;
        if (Float.compare(trip.getCost(), getCost()) != 0) return false;
        if (getStatus() != trip.getStatus()) return false;
        if (getRequestDate() != null ? !getRequestDate().equals(trip.getRequestDate()) : trip.getRequestDate() != null)
            return false;
        if (getPickupLocation() != null ? !getPickupLocation().equals(trip.getPickupLocation()) : trip.getPickupLocation() != null)
            return false;
        if (getDropoffLocation() != null ? !getDropoffLocation().equals(trip.getDropoffLocation()) : trip.getDropoffLocation() != null)
            return false;
        if (getPickupDate() != null ? !getPickupDate().equals(trip.getPickupDate()) : trip.getPickupDate() != null)
            return false;
        if (getDropoffDate() != null ? !getDropoffDate().equals(trip.getDropoffDate()) : trip.getDropoffDate() != null)
            return false;
        if (getType() != null ? !getType().equals(trip.getType()) : trip.getType() != null)
            return false;
        if (getDriverName() != null ? !getDriverName().equals(trip.getDriverName()) : trip.getDriverName() != null)
            return false;
        if (getDriverPic() != null ? !getDriverPic().equals(trip.getDriverPic()) : trip.getDriverPic() != null)
            return false;
        if (getCarMake() != null ? !getCarMake().equals(trip.getCarMake()) : trip.getCarMake() != null)
            return false;
        if (getCarModel() != null ? !getCarModel().equals(trip.getCarModel()) : trip.getCarModel() != null)
            return false;
        if (getCarNumber() != null ? !getCarNumber().equals(trip.getCarNumber()) : trip.getCarNumber() != null)
            return false;
        if (getCarPic() != null ? !getCarPic().equals(trip.getCarPic()) : trip.getCarPic() != null)
            return false;
        if (getDurationUnit() != null ? !getDurationUnit().equals(trip.getDurationUnit()) : trip.getDurationUnit() != null)
            return false;
        if (getDistanceUnit() != null ? !getDistanceUnit().equals(trip.getDistanceUnit()) : trip.getDistanceUnit() != null)
            return false;
        if (getCostUnit() != null ? !getCostUnit().equals(trip.getCostUnit()) : trip.getCostUnit() != null)
            return false;
        if (getPickupLat() != null ? !getPickupLat().equals(trip.getPickupLat()) : trip.getPickupLat() != null)
            return false;
        if (getPickupLng() != null ? !getPickupLng().equals(trip.getPickupLng()) : trip.getPickupLng() != null)
            return false;
        if (getDropoffLat() != null ? !getDropoffLat().equals(trip.getDropoffLat()) : trip.getDropoffLat() != null)
            return false;
        return getDropoffLng() != null ? getDropoffLng().equals(trip.getDropoffLng()) : trip.getDropoffLng() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getRequestDate() != null ? getRequestDate().hashCode() : 0);
        result = 31 * result + (getPickupLocation() != null ? getPickupLocation().hashCode() : 0);
        result = 31 * result + (getDropoffLocation() != null ? getDropoffLocation().hashCode() : 0);
        result = 31 * result + (getPickupDate() != null ? getPickupDate().hashCode() : 0);
        result = 31 * result + (getDropoffDate() != null ? getDropoffDate().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getDriverName() != null ? getDriverName().hashCode() : 0);
        result = 31 * result + (getDriverPic() != null ? getDriverPic().hashCode() : 0);
        result = 31 * result + (getCarMake() != null ? getCarMake().hashCode() : 0);
        result = 31 * result + (getCarModel() != null ? getCarModel().hashCode() : 0);
        result = 31 * result + (getCarNumber() != null ? getCarNumber().hashCode() : 0);
        result = 31 * result + (getCarPic() != null ? getCarPic().hashCode() : 0);
        result = 31 * result + (getDurationUnit() != null ? getDurationUnit().hashCode() : 0);
        result = 31 * result + (getDistanceUnit() != null ? getDistanceUnit().hashCode() : 0);
        result = 31 * result + (getCostUnit() != null ? getCostUnit().hashCode() : 0);
        result = 31 * result + (getPickupLat() != null ? getPickupLat().hashCode() : 0);
        result = 31 * result + (getPickupLng() != null ? getPickupLng().hashCode() : 0);
        result = 31 * result + (getDropoffLat() != null ? getDropoffLat().hashCode() : 0);
        result = 31 * result + (getDropoffLng() != null ? getDropoffLng().hashCode() : 0);
        result = 31 * result + getDriverId();
        result = 31 * result + getDriverRating();
        result = 31 * result + getCarYear();
        result = 31 * result + getDuration();
        result = 31 * result + (getDistance() != +0.0f ? Float.floatToIntBits(getDistance()) : 0);
        result = 31 * result + (getCost() != +0.0f ? Float.floatToIntBits(getCost()) : 0);
        return result;
    }

    @NotNull
    public static List<Trip> filtered(List<Trip> trips, String query) {
        if (query == null || query.isEmpty()) return trips;
        List<Trip> _trips = new ArrayList<>();
        for (Trip trip : trips) {
            if (trip.ftsSearchable().contains(query.toLowerCase(ROOT))) _trips.add(trip);
        }
        return _trips;
    }

    public static class Builder {
        private final Long id;
        private Status status;
        private String requestDate, pickupLocation, dropoffLocation, pickupDate, dropoffDate,
                type, driverName, driverPic, carMake, carModel, carNumber, carPic, durationUnit,
                distanceUnit, costUnit;
        private Double pickupLat, pickupLng, dropoffLat, dropoffLng;
        private int driverId, driverRating, carYear, duration;
        private float distance, cost;

        public Builder(Long id) {
            this.id = id;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setRequestDate(String requestDate) {
            this.requestDate = requestDate;
            return this;
        }

        public Builder setPickupLocation(String pickupLocation) {
            this.pickupLocation = pickupLocation;
            return this;
        }

        public Builder setDropoffLocation(String dropoffLocation) {
            this.dropoffLocation = dropoffLocation;
            return this;
        }

        public Builder setPickupDate(String pickupDate) {
            this.pickupDate = pickupDate;
            return this;
        }

        public Builder setDropoffDate(String dropoffDate) {
            this.dropoffDate = dropoffDate;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setDriverName(String driverName) {
            this.driverName = driverName;
            return this;
        }

        public Builder setDriverPic(String driverPic) {
            this.driverPic = driverPic;
            return this;
        }

        public Builder setCarMake(String carMake) {
            this.carMake = carMake;
            return this;
        }

        public Builder setCarModel(String carModel) {
            this.carModel = carModel;
            return this;
        }

        public Builder setCarNumber(String carNumber) {
            this.carNumber = carNumber;
            return this;
        }

        public Builder setCarPic(String carPic) {
            this.carPic = carPic;
            return this;
        }

        public Builder setDurationUnit(String durationUnit) {
            this.durationUnit = durationUnit;
            return this;
        }

        public Builder setDistanceUnit(String distanceUnit) {
            this.distanceUnit = distanceUnit;
            return this;
        }

        public Builder setCostUnit(String costUnit) {
            this.costUnit = costUnit;
            return this;
        }

        public Builder setPickupLat(Double pickupLat) {
            this.pickupLat = pickupLat;
            return this;
        }

        public Builder setPickupLng(Double pickupLng) {
            this.pickupLng = pickupLng;
            return this;
        }

        public Builder setDropoffLat(Double dropoffLat) {
            this.dropoffLat = dropoffLat;
            return this;
        }

        public Builder setDropoffLng(Double dropoffLng) {
            this.dropoffLng = dropoffLng;
            return this;
        }

        public Builder setDriverId(int driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder setDriverRating(int driverRating) {
            this.driverRating = driverRating;
            return this;
        }

        public Builder setCarYear(int carYear) {
            this.carYear = carYear;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setDistance(float distance) {
            this.distance = distance;
            return this;
        }

        public Builder setCost(float cost) {
            this.cost = cost;
            return this;
        }

        public Trip build() {
            Trip trip = new Trip();
            trip.setId(this.id);
            trip.setStatus(this.status);
            trip.setRequestDate(this.requestDate);
            trip.setPickupLocation(this.pickupLocation);
            trip.setDropoffLocation(this.dropoffLocation);
            trip.setPickupDate(this.pickupDate);
            trip.setDropoffDate(this.dropoffDate);
            trip.setType(this.type);
            trip.setDriverName(this.driverName);
            trip.setDriverPic(this.driverPic);
            trip.setCarMake(this.carMake);
            trip.setCarModel(this.carModel);
            trip.setCarNumber(this.carNumber);
            trip.setCarPic(this.carPic);
            trip.setDurationUnit(this.durationUnit);
            trip.setDistanceUnit(this.distanceUnit);
            trip.setCostUnit(this.costUnit);
            trip.setPickupLat(this.pickupLat);
            trip.setPickupLng(this.pickupLng);
            trip.setDropoffLat(this.dropoffLat);
            trip.setDropoffLng(this.dropoffLng);
            trip.setDriverId(this.driverId);
            trip.setDriverRating(this.driverRating);
            trip.setCarYear(this.carYear);
            trip.setDuration(this.duration);
            trip.setDistance(this.distance);
            trip.setCost(this.cost);
            return trip;
        }
    }
}
