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
            Models.City location = e.SelectedItem as City;

            var navigationParameter = new Dictionary<string, object>
            {
      
                {"city", location }
                
            };

            _ = Current.GoToAsync($"entrydetails", navigationParameter);
        }
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