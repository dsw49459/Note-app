import SwiftUI

@main
struct iOSApp: App {

    init() {
        DiModule.koin
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}