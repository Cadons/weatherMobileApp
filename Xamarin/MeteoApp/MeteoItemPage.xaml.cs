using MeteoApp.Models;

namespace MeteoApp;

[QueryProperty(nameof(City), "city")]
public partial class MeteoItemPage : ContentPage
{
 public   City City { get; set; }

    public MeteoItemPage()
    {
        InitializeComponent();
        BindingContext = new ViewModels.SelectedItemViewModel();
    }
    protected override void OnAppearing()
    {
        base.OnAppearing();
        if (BindingContext is ViewModels.SelectedItemViewModel vm)
        {
            if (City != null)
                vm.City = City;
        }
    }

}