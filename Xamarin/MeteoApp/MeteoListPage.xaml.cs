using MeteoApp.Models;
using MeteoApp.Repository;
using MeteoApp.ViewModels;
namespace MeteoApp;

public partial class MeteoListPage : Shell
{
    public Dictionary<string, Type> Routes { get; private set; } = new Dictionary<string, Type>();

    public MeteoListPage()
	{
		InitializeComponent();
        RegisterRoutes();

        BindingContext = new MeteoListViewModel();
    }

    private void RegisterRoutes()
    {
        Routes.Add("entrydetails", typeof(MeteoItemPage));

        foreach (var item in Routes)
            Routing.RegisterRoute(item.Key, item.Value);
    }

    private async void OnListItemSelected(object sender, SelectedItemChangedEventArgs e)
    {
        if (e.SelectedItem != null)
        {
            Models.Entry entry = e.SelectedItem as Models.Entry;

            var navigationParameter = new Dictionary<string, object>
            {
                { "Entry", entry }
            };
          ShowPosition();
            Shell.Current.GoToAsync($"entrydetails", navigationParameter);
        }
    }
    public async void ShowPosition()
    {
        var name = await WeatherRepository.Instance.GetWeatherFromGPSAsync();
        _ = ShowPrompt(name.Name+" "+name.Id);
        var x = await WeatherRepository.Instance.GetWeatherByCity("Sorengo");
        _ = ShowPrompt(x.Name+" "+x.Id);
    }

    private void OnItemAdded(object sender, EventArgs e)
    {
         _ = ShowPrompt();
    }

    private async Task ShowPrompt(string s="To implement")
    {
        await DisplayAlert("Add City", s, "OK");
    }
}