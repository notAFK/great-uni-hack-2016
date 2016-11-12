<?php

namespace App\Http\Controllers;

use Auth;
use Validator;
use App\Event;
use App\User;
use Illuminate\Http\Request;

class EventController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        if(Auth::check()) {
            return view('in.events.index')->with('events', Event::all())
                                          ->with('my_events', Auth::user()->events);
        }

        $events = Event::all();
        return view('events.index')->with('events', $events);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('in.events.create');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $messages = [
            'title.required'            =>  'Title is required.',
            'location_name.required'    =>  'Location is required.',
            'start_date.required'       =>  'Start date&time is required.',
            'end_date.required'         =>  'End date&time is required.',
            'password.required'         =>  'Event password is required.',
        ];
        $validator = Validator::make($request->all(), [
            'title'         =>  'required',
            'location_name' =>  'required',
            'start_date'    =>  'required',
            'end_date'      =>  'required',
            'password'      =>  'required',
        ], $messages);

        if($validator->fails()) {
            return redirect()->back()->withErrors($validator);
        }

        $event = new Event;
        $event->title = $request->input('title');
        $event->slug = str_slug($request->input('title'), '-');
        $event->location_name = $request->input('location_name');
        $event->start_date = $request->input('start_date');
        $event->end_date = $request->input('end_date');
        $event->password = $request->input('password');
        $event->user_id = Auth::id();
        $event->save();

        return redirect()->to('/events/' . $event->id);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show(Request $request, $id)
    {
        $event = Event::find($id);
        if(!$event) {
            return view('events.index')->withErrors(['event' => 'No event with this id.']);
        }

        if($event->password == $request->input('password')) {
            return view('events.show')->with('event', $event);
        }

        return view('events.index')->withErrors(['password' => 'Wrong password.']);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
