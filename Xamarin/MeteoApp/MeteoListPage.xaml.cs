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

    private void OnListItemSelected(object sender, SelectedItemChangedEventArgs e)
    {
        if (e.SelectedItem != null)
        {

            LoadPage(e.SelectedItem);
        }
    }
    

    private void OnItemAdded(object sender, EventArgs e)
    {
         _ = ShowPrompt();
    }

    private async Task ShowPrompt()
    {
        //show prompt alert

        string result = await DisplayPromptAsync("Add new location", "Insert location name");

        await DisplayAlert("Add City", result, "OK");
    }

    private  void Geolocate_Clicked(object sender, EventArgs e)
    {
      
      
        LoadPage(null,true);

    }

    private async void LoadPage(object e, bool isGps=false)
    {
        City location=new City();
        IWeatherRepository repository = WeatherRepository.Instance;
        this.LoadingSpinner.IsRunning = true;
        if (!isGps)
        {
            location = e as City;
            
            location.WeatherData =await repository.GetWeatherByCity(location.Name);
        }
        else
        {
            WeatherData meteoAndLocation = await repository.GetWeatherFromGPSAsync();
            location.Name = meteoAndLocation.Name;
            location.WeatherData = meteoAndLocation;
        }

        

        SelectedItemViewModel vm = new SelectedItemViewModel();
        vm.City = location;

        var navigationParameter = new Dictionary<string, object>
            {
                {"vm", vm }
            };
        this.LoadingSpinner.IsRunning = false;
        _ = Current.GoToAsync($"entrydetails", navigationParameter);

    }

}