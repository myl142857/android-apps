# TIPS & TRICKS #

## 1. Open a URL in a browser window ##

```
   Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://url"));
   startActivity(myIntent);
```

## 2. Show an alert dialog ##
```
   new AlertDialog.Builder(CurrentClass.this).setMessage("message").setPositiveButton("OK", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int whichButton) {}}).show();
```


## 3. If you are going to use Internet connection... ##
Don't forget to add this line in AndroidManifest.xml:
```
    <uses-permission android:name="android.permission.INTERNET" />
```

## 4. Show the debug output in a terminal ##
To see the debug output in a terminal run, while executing the emulator, the command:
` $ adb logcat *:E `

## 5. Show a short notification ##
This line shows a notification with a small message:
```
   Toast.makeText(WorkDetails.this, "Text to show", Toast.LENGTH_SHORT).show();
```
If you want the notification to last longer, use `Toast.LENGTH_LONG` instead of `Toast.LENGHT_SHORT`.

## 6. Change between vertical and horizontal view in the emulator ##
Just press `Ctrl + F12`.