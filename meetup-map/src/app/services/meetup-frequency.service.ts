import { Injectable, NgZone  } from '@angular/core';
import { Observable } from "rxjs";
import { environment } from 'src/environments/environment';

type OnMessageCallback = (data: MessageEvent['data']) => void

type OnErrorCallback = () => void

@Injectable({
  providedIn: 'root'
})
export class MeetupFrequencyService {

  getData(onMessageCallback: OnMessageCallback, onErrorCallback: OnErrorCallback) {
    let sse = new EventSource('http://localhost:8080/meetupRsvps');

    sse.onmessage = function(e) {
      onMessageCallback(e.data);
    }

    sse.onerror = function() {
      onErrorCallback()
    }
  }
  
  // constructor(private _zone: NgZone) {}

  
  
  // getServerSentEvent(): Observable<MessageEvent> {
  //   return Observable.create(observer => {
  //     const eventSource = this.getEventSource(environment.meetUpFrequencyUrl);

  //     eventSource.onmessage = event => {
  //       this._zone.run(() => {
  //         observer.next(event);
  //         // eventSource.close();
  //       });
  //     };
  //     eventSource.onopen = event => {
  //       this._zone.run(() => {
  //          // Reset reconnect frequency upon successful connection
  //         this.reconnectFrequencySeconds = 1;
  //       });
  //     };
  //     eventSource.onerror = error => {
  //       this._zone.run(() => {
  //         // observer.error(error);
  //         eventSource.close();
  //         // this.reconnectFunc();
  //       });
  //     };
  //   });
  // }
  
  // private getEventSource(url: string): EventSource {
  //   return new EventSource(url);
  // }

//   // reconnectFrequencySeconds doubles every retry
// private reconnectFrequencySeconds = 1;
// private evtSource;

// reconnectFunc = function() { setTimeout(this.tryToSetupFunc.bind(this), this.waitFunc()) };
// waitFunc = function() { return this.reconnectFrequencySeconds * 1000 };
// tryToSetupFunc = () => {
//   // debugger;
//   this.getServerSentEvent();
//     this.reconnectFrequencySeconds *= 2;
//     if (this.reconnectFrequencySeconds >= 64) {
//       this.reconnectFrequencySeconds = 64;
//     }
// };
}
