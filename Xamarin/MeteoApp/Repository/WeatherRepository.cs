﻿using MeteoApp.Models;
using Newtonsoft.Json;

namespace MeteoApp.Repository
{
    internal class WeatherRepository : IWeatherRepository

    {
        private const string _API_KEY = "8ee77de7c0d74ad71c1aa7e069710ff7";
        private const string _API_URL = "https://api.openweathermap.org/data/2.5/weather?";
        private static WeatherRepository _instance;
        private WeatherRepository()
        {
        }
        public static WeatherRepository Instance
        {
            get
            {
                if (_instance == null)
                {
                    _instance = new WeatherRepository();
                }
                return _instance;
            }
        }
        public async Task<WeatherData> GetWeatherByCity(string city)
        {
            WeatherData weather = _parseJSON(_getHttpJson(string.Format("{0}q={1}&appid={2}", _API_URL, city, _API_KEY)));
            return weather;
        }

        public async Task<WeatherData> GetWeatherFromGPSAsync()
        {
            Models.Location location = new Models.Location(0, 0);
            var gps = await Geolocation.Default.GetLocationAsync();
            if (gps != null)
            {
                location.Latitude = gps.Latitude;
                location.Longitude = gps.Longitude;
            }


            WeatherData weather = _parseJSON(_getHttpJson(string.Format("{0}lat={1}&lon={2}&appid={3}",_API_URL,location.Latitude,location.Longitude,_API_KEY)));
            return weather;

        }
        private string _getHttpJson(string url)
        {
            var httpClient = new HttpClient();
            var json = httpClient.GetStringAsync(url);
            return json.Result;
        }
        private WeatherData _parseJSON(string json)
        {
            var weather = JsonConvert.DeserializeObject<WeatherData>(json);
            return weather;
        }

    }
}
