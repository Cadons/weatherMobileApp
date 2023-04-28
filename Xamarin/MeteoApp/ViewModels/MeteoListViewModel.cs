using MeteoApp.Models;
using MeteoApp.Repository;
using System.Collections.ObjectModel;
using Entry = MeteoApp.Models.Entry;

namespace MeteoApp.ViewModels
{
    public class MeteoListViewModel : BaseViewModel
    {
        ObservableCollection<Entry> _entries;

        public ObservableCollection<Entry> Entries
        {
            get { return _entries; }
            set
            {
                _entries = value;
                OnPropertyChanged();
            }
        }
       

       

        public MeteoListViewModel()
        {
            Entries = new ObservableCollection<Entry>();

            for (var i = 0; i < 10; i++)
            {
                var e = new Entry
                {
                    Id = i
                };

                Entries.Add(e);
            }
        }
    }
}
