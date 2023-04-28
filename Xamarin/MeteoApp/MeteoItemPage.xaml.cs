using MeteoApp.Models;

namespace MeteoApp;

[QueryProperty(nameof(Entry), "Entry")]
public partial class MeteoItemPage : ContentPage
{
    Models.Entry entry;
    public Models.Entry Entry
    {
        get => entry;
        set
        {
            entry = value;
            OnPropertyChanged();
        }
    }

    public MeteoItemPage()
    {
        InitializeComponent();
        BindingContext = this;
    }

    protected override void OnAppearing()
    {
        base.OnAppearing();
    }
}