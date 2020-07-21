import { Component, OnDestroy } from '@angular/core';
import { MeetupFrequencyService } from './services/meetup-frequency.service';
import { VenueFrequency } from './model/VenueFrequency';
import { Subscription, interval } from 'rxjs';
import { VenueCoordItem } from './model/VenueCoordItem';

// declare var google: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnDestroy {
  constructor(private sseService: MeetupFrequencyService) {}
  lat: number = 37.774546;
  lng: number = -122.433523;
  private map: google.maps.Map;
  private heatmap: any;
  private coords = [];
  mySub: Subscription;
  private venueCoordMap = new Map();

  onMapLoad(mapInstance: google.maps.Map) {
    this.map = mapInstance;

    this.heatmap = new google.maps.visualization.HeatmapLayer({
      map: this.map,
      data: this.coords,
      radius: 15
    });

    this.sseService.getMeetUpData (this.onMessageSuccess.bind(this));

    this.mySub = interval(30000).subscribe((func => {
      this.updateCoordArray(this.venueCoordMap);
      if(this.coords.length > 0) {
        this.heatmap.setData(this.coords);
      }
    }))
}

onMessageSuccess(data: MessageEvent['data']) {
  const venueFrequency : VenueFrequency = JSON.parse(data);
  this.updateVenueCoordMap(venueFrequency);
}

updateVenueCoordMap(venueFrequency: VenueFrequency) {
  this.venueCoordMap.set(
    venueFrequency.venueFrequencyKey.venue_id, 
    new VenueCoordItem(
      venueFrequency.venueFrequencyKey.lat, 
      venueFrequency.venueFrequencyKey.lon, 
      venueFrequency.count
    )
  );
}

updateCoordArray(venueCoordMap: Map<number,VenueCoordItem>) {
  this.coords.splice(0,this.coords.length);
  venueCoordMap.forEach((value: VenueCoordItem, _) => {
    this.coords.push({location: (this.formatLatLng(value.lat, value.lon)), weight: value.weight})
});
}

formatToHeatMapData(venueFrequency: VenueFrequency) {
  return {
    location: this.formatLatLng(
      venueFrequency.venueFrequencyKey.lat, 
      venueFrequency.venueFrequencyKey.lon), 
    weight: venueFrequency.count
  };
}

formatLatLng(lat: number, lon: number) {
  return new google.maps.LatLng(lat, lon);
}

ngOnDestroy() {
  this.mySub.unsubscribe();
}
}
