//
//  DiModule.swift
//  iosApp
//
//  Created by Krzysztof Farys on 20/01/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import ComposeApp

class DiModule {
    static var koin = {
        KoinInit().doInit(
                appDeclaration: { _ in
                    // Do nothing
                }
        )
    }()
}
