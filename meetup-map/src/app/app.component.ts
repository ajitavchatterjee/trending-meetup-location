import { Component, ElementRef, ViewChild, OnDestroy, OnInit } from '@angular/core';
import { MeetupFrequencyService } from './services/meetup-frequency.service';
import { VenueFrequency } from './model/VenueFrequency';
import { Subscription, interval } from 'rxjs';
import { VenueCoordItem } from './model/VenueCoordItem';

declare var google: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnDestroy {
  constructor(private sseService: MeetupFrequencyService) {}
  lat = 51.678418;
  lng = 7.809007;
  private map: google.maps.Map;
  private heatmap: any;
  private coords = [];
  mySub: Subscription;
  private venueCoord = new Map();

  onMapLoad(mapInstance: google.maps.Map) {
    this.map = mapInstance;

    this.heatmap = new google.maps.visualization.HeatmapLayer({
      map: this.map,
      data: this.coords
  });

  this.sseService.getMeetUpData( this.onMessageSuccess.bind(this), () => {
    //handle the error here
  })


    // call update map to array
    this.mySub = interval(30000).subscribe((func => {
      // this.updateCoordArray(this.venueCoord);
      if(this.coords.length > 0) {
        this.heatmap.setData(this.coords);
      }
    }))

    // here our in other method after you get the coords; but make sure map is loaded
    // this.sseService.getServerSentEvent().subscribe((message) => {
    //   const obj = JSON.parse(message.data);
    //   console.log(obj);
    //   this.initHeatMap(obj.venueFrequencyKey.lat,obj.venueFrequencyKey.lon, obj.count, obj.venueFrequencyKey.venue_id)
    // });
}

onMessageSuccess(data: MessageEvent['data']) {
  const obj : VenueFrequency = JSON.parse(data);
  console.log(obj);
  const temp = [...this.coords];
  const index = temp.findIndex( t => t.venue_id === obj.venueFrequencyKey.venue_id)
  if(index > -1) {
    temp[index] = {location: this.formatLatLng(obj.venueFrequencyKey.lat, obj.venueFrequencyKey.lon), weight: obj.count};
  } else {
    temp.push({location: this.formatLatLng(obj.venueFrequencyKey.lat, obj.venueFrequencyKey.lon), weight: obj.count})
  }
  this.coords = temp;
  // this.coords = obj.map(t => ({location: (this.formatLatLng(t.venueFrequencyKey.lat, t.venueFrequencyKey.lon)), weight: t.count}))
  // this.heatmap.setData(this.coords);
  // this.updateVenueMap(obj.venueFrequencyKey.lat,obj.venueFrequencyKey.lon, obj.count, obj.venueFrequencyKey.venue_id)
  // this.coords.push({location: (this.formatLatLng(value.lat, value.lon)), weight: value.weight})
}

onMessageError() {

}

  // initHeatMap(lat: number, lon: number, weight: number, venue_id: number) {
  //   this.updateVenueMap(lat, lon, weight, venue_id);
  // }

  updateVenueMap(lat: number, lon: number, weight: number, venue_id: number) {
    this.venueCoord.set(venue_id, new VenueCoordItem(lat, lon, weight));
    debugger;
  }

  formatLatLng(lat: number, lon: number) {
    return new google.maps.LatLng(lat, lon);
  }

  updateCoordArray(venueCoord: Map<number,VenueCoordItem>) {
    this.coords.splice(0,this.coords.length);
    for (let [key, value] of venueCoord) {
      this.coords.push({location: (this.formatLatLng(value.lat, value.lon)), weight: value.weight})
    }
  }

  ngOnDestroy() {
    this.mySub.unsubscribe();
  }
}
