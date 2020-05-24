package com.example.notepadapp.bean;

public class WeatherBean {

    /**
     * location : {"name":"Red Vale","region":"","country":"New Zealand","lat":-36.7,"lon":174.7,"tz_id":"Pacific/Auckland","localtime_epoch":1590204178,"localtime":"2020-05-23 15:22"}
     * current : {"last_updated_epoch":1590203278,"last_updated":"2020-05-23 15:07","temp_c":15,"temp_f":59,"is_day":1,"condition":{"text":"Overcast","icon":"//cdn.weatherapi.com/weather/64x64/day/122.png","code":1009},"wind_mph":2.2,"wind_kph":3.6,"wind_degree":141,"wind_dir":"SE","pressure_mb":1021,"pressure_in":30.6,"precip_mm":0,"precip_in":0,"humidity":46,"cloud":98,"feelslike_c":15.8,"feelslike_f":60.4,"vis_km":10,"vis_miles":6,"uv":2,"gust_mph":2.5,"gust_kph":4}
     */

    private LocationBean location;
    private CurrentBean current;


    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public CurrentBean getCurrent() {
        return current;
    }

    public void setCurrent(CurrentBean current) {
        this.current = current;
    }

    public static class LocationBean {
        /**
         * name : Red Vale
         * region :
         * country : New Zealand
         * lat : -36.7
         * lon : 174.7
         * tz_id : Pacific/Auckland
         * localtime_epoch : 1590204178
         * localtime : 2020-05-23 15:22
         */

        private String name;
        private String region;
        private String country;
        private double lat;
        private double lon;
        private String tz_id;
        private int localtime_epoch;
        private String localtime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getTz_id() {
            return tz_id;
        }

        public void setTz_id(String tz_id) {
            this.tz_id = tz_id;
        }

        public int getLocaltime_epoch() {
            return localtime_epoch;
        }

        public void setLocaltime_epoch(int localtime_epoch) {
            this.localtime_epoch = localtime_epoch;
        }

        public String getLocaltime() {
            return localtime;
        }

        public void setLocaltime(String localtime) {
            this.localtime = localtime;
        }
    }

    public static class CurrentBean {
        /**
         * last_updated_epoch : 1590203278
         * last_updated : 2020-05-23 15:07
         * temp_c : 15.0
         * temp_f : 59.0
         * is_day : 1
         * condition : {"text":"Overcast","icon":"//cdn.weatherapi.com/weather/64x64/day/122.png","code":1009}
         * wind_mph : 2.2
         * wind_kph : 3.6
         * wind_degree : 141
         * wind_dir : SE
         * pressure_mb : 1021.0
         * pressure_in : 30.6
         * precip_mm : 0.0
         * precip_in : 0.0
         * humidity : 46
         * cloud : 98
         * feelslike_c : 15.8
         * feelslike_f : 60.4
         * vis_km : 10.0
         * vis_miles : 6.0
         * uv : 2.0
         * gust_mph : 2.5
         * gust_kph : 4.0
         */

        private int last_updated_epoch;
        private String last_updated;
        private double temp_c;
        private double temp_f;
        private int is_day;
        private ConditionBean condition;
        private double wind_mph;
        private double wind_kph;
        private int wind_degree;
        private String wind_dir;
        private double pressure_mb;
        private double pressure_in;
        private double precip_mm;
        private double precip_in;
        private int humidity;
        private int cloud;
        private double feelslike_c;
        private double feelslike_f;
        private double vis_km;
        private double vis_miles;
        private double uv;
        private double gust_mph;
        private double gust_kph;

        public int getLast_updated_epoch() {
            return last_updated_epoch;
        }

        public void setLast_updated_epoch(int last_updated_epoch) {
            this.last_updated_epoch = last_updated_epoch;
        }

        public String getLast_updated() {
            return last_updated;
        }

        public void setLast_updated(String last_updated) {
            this.last_updated = last_updated;
        }

        public double getTemp_c() {
            return temp_c;
        }

        public void setTemp_c(double temp_c) {
            this.temp_c = temp_c;
        }

        public double getTemp_f() {
            return temp_f;
        }

        public void setTemp_f(double temp_f) {
            this.temp_f = temp_f;
        }

        public int getIs_day() {
            return is_day;
        }

        public void setIs_day(int is_day) {
            this.is_day = is_day;
        }

        public ConditionBean getCondition() {
            return condition;
        }

        public void setCondition(ConditionBean condition) {
            this.condition = condition;
        }

        public double getWind_mph() {
            return wind_mph;
        }

        public void setWind_mph(double wind_mph) {
            this.wind_mph = wind_mph;
        }

        public double getWind_kph() {
            return wind_kph;
        }

        public void setWind_kph(double wind_kph) {
            this.wind_kph = wind_kph;
        }

        public int getWind_degree() {
            return wind_degree;
        }

        public void setWind_degree(int wind_degree) {
            this.wind_degree = wind_degree;
        }

        public String getWind_dir() {
            return wind_dir;
        }

        public void setWind_dir(String wind_dir) {
            this.wind_dir = wind_dir;
        }

        public double getPressure_mb() {
            return pressure_mb;
        }

        public void setPressure_mb(double pressure_mb) {
            this.pressure_mb = pressure_mb;
        }

        public double getPressure_in() {
            return pressure_in;
        }

        public void setPressure_in(double pressure_in) {
            this.pressure_in = pressure_in;
        }

        public double getPrecip_mm() {
            return precip_mm;
        }

        public void setPrecip_mm(double precip_mm) {
            this.precip_mm = precip_mm;
        }

        public double getPrecip_in() {
            return precip_in;
        }

        public void setPrecip_in(double precip_in) {
            this.precip_in = precip_in;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getCloud() {
            return cloud;
        }

        public void setCloud(int cloud) {
            this.cloud = cloud;
        }

        public double getFeelslike_c() {
            return feelslike_c;
        }

        public void setFeelslike_c(double feelslike_c) {
            this.feelslike_c = feelslike_c;
        }

        public double getFeelslike_f() {
            return feelslike_f;
        }

        public void setFeelslike_f(double feelslike_f) {
            this.feelslike_f = feelslike_f;
        }

        public double getVis_km() {
            return vis_km;
        }

        public void setVis_km(double vis_km) {
            this.vis_km = vis_km;
        }

        public double getVis_miles() {
            return vis_miles;
        }

        public void setVis_miles(double vis_miles) {
            this.vis_miles = vis_miles;
        }

        public double getUv() {
            return uv;
        }

        public void setUv(double uv) {
            this.uv = uv;
        }

        public double getGust_mph() {
            return gust_mph;
        }

        public void setGust_mph(double gust_mph) {
            this.gust_mph = gust_mph;
        }

        public double getGust_kph() {
            return gust_kph;
        }

        public void setGust_kph(double gust_kph) {
            this.gust_kph = gust_kph;
        }

        public static class ConditionBean {
            /**
             * text : Overcast
             * icon : //cdn.weatherapi.com/weather/64x64/day/122.png
             * code : 1009
             */

            private String text;
            private String icon;
            private int code;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }
        }
    }

    public static class ConditionsBean {

        /**
         * code : 1000
         * day : Sunny
         * night : Clear
         * icon : 113
         */

        private int code;
        private String day;
        private String night;
        private int icon;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getNight() {
            return night;
        }

        public void setNight(String night) {
            this.night = night;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }
}

