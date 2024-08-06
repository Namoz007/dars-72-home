import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MainApp());
}

class MainApp extends StatefulWidget {
  const MainApp({super.key});

  @override
  State<MainApp> createState() => _MainAppState();
}

class _MainAppState extends State<MainApp> {
  static const platform = MethodChannel('com.example.yourapp/flashlight');
  bool flashLight = false;

  Future<void> _toggleFlashlight(bool status) async {
    try {
      await platform.invokeMethod('toggleFlashlight', {'status': status});
    } on PlatformException catch (e) {
      print("Failed to toggle flashlight: '${e.message}'.");
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        body: Center(
          child: SwitchListTile(
            value: flashLight,
            onChanged: (value) async{
              setState(() {
                flashLight = value;
              });
              _toggleFlashlight(value);
            },
          ),
        ),
      ),
    );
  }
}
