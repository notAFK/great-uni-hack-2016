<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Support\Facades\Auth;

class EventOwner
{
    public function handle($request, Closure $next, $event_id)
    {
        if(Event::find($event_id)->organiser->id == Auth::id()) {
            return $next($request);
        }

        return redirect()->back()
                         ->withErrors(['auth' => 'Not authorised.']);
    }
}
