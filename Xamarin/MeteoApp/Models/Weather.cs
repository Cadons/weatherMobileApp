namespace MeteoApp.Models
{
    public class Weather
    {
        //openweathermap.org API json object
        private double mTemperature;
        private double mPressure;
        private double mHumidity;
        private double mTempMin;
        private double mTempMax;
        private double mWindSpeed;
        private double mWindDeg;
        private double mClouds;
        private string mIcon;
        private double mRain; //3h rain volume
        private double mSnow; //3h snow volume
        private string mWeatherDescription;

        public Weather()
        {
        }

        public double Temperature
        {
            get { return mTemperature; }
            set { mTemperature = value; }
        }

        public double Pressure
        {
            get { return mPressure; }
            set { mPressure = value; }
        }

        public double Humidity
        {
            get { return mHumidity; }
            set { mHumidity = value; }
        }

        public double TempMin
        {
            get { return mTempMin; }
            set { mTempMin = value; }
        }

        public double TempMax
        {
            get { return mTempMax; }
            set { mTempMax = value; }
        }

        public double WindSpeed
        {
            get { return mWindSpeed; }
            set { mWindSpeed = value; }
        }

        public double WindDeg
        {
            get { return mWindDeg; }
            set { mWindDeg = value; }
        }

        public double Clouds
        {
            get { return mClouds; }
            set { mClouds = value; }
        }

        public double Rain
        {
            get { return mRain; }
            set { mRain = value; }
        }

        public double Snow
        {
            get { return mSnow; }
            set { mSnow = value; }
        }

        public string WeatherDescription
        {
            get { return mWeatherDescription; }
            set { mWeatherDescription = value; }
        }

        public string Icon
        {
            get { return mIcon; }
            set { mIcon = value; }
        }



    }

}
