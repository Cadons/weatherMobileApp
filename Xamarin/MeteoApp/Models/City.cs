namespace MeteoApp.Models
{
    public class City
    {
        public int ID { get; set; }
        public string Name { get; set; }
        public WeatherData WeatherData { get; set; }
    }
}
