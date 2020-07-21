import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

type OnMessageCallback = (data: MessageEvent['data']) => void

@Injectable({
  providedIn: 'root'
})
export class MeetupFrequencyService {

  getMeetUpData(onMessageCallback: OnMessageCallback) {
    let sse = new EventSource(environment.meetUpFrequencyUrl);

    sse.onmessage = function(e) {
      onMessageCallback(e.data);
    }

    sse.onerror = function(e) {
      console.log(e);
    }
  }
}
