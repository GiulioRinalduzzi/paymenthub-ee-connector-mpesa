# paymenthub-ee-connector-mpesa

A Payment Hub EE connector that moves money through Safaricom M-Pesa: it starts a customer
payment (STK push / Lipa na M-Pesa), follows it until M-Pesa says it is done, and reports the
result back to the workflow.

[![License](https://img.shields.io/badge/License-MPL--2.0-blue.svg)](LICENSE)

## What it does

- Gets and caches an OAuth access token from Safaricom, and renews it when it expires.
- Starts a payment on M-Pesa (buy goods / STK push) for a transfer coming from the workflow.
- Asks M-Pesa for the status of a payment, and retries when the error is a temporary one.
- Receives the callbacks M-Pesa sends back (`/buygoods/callback`, `/confirmation`,
  `/validation`) and turns them into workflow messages.
- Runs the paybill flow, where a customer pays into a paybill number and the connector
  starts the matching workflow.
- Runs Zeebe (Camunda) workers for `init-transfer`, `get-transaction-status` and
  `delete-workflow-instancekey`.

## How it fits into Payment Hub EE

Payment Hub EE runs each payment as a Zeebe (Camunda) workflow. When a workflow reaches the
step that has to actually move the money through M-Pesa, this connector's Zeebe workers pick
up that job, call the Safaricom API over HTTP, and report back. M-Pesa answers twice: once
immediately, then again later with a callback. The connector listens for that callback on its
own REST endpoints and publishes a message to the waiting workflow. So it sits between the
Payment Hub orchestration layer and Safaricom, translating between the two.

Safaricom API documentation: [developer.safaricom.co.ke](https://developer.safaricom.co.ke/)

## Tech stack

- Java 21
- Spring Boot 3.4
- Apache Camel 4 (routes for token, payment, status and callback flows)
- Zeebe / Camunda workers (via the Zeebe Java client)
- Gradle build
- Depends on `paymenthub-ee-bom` (for versions) and `paymenthub-ee-core`

## Build and run

    ./gradlew clean build          # compiles and runs the tests
    ./gradlew bootRun              # runs the connector locally
    docker build -t paymenthub-ee-connector-mpesa .

The connector listens on port 5000 for the Camel REST routes and 8080 for Spring Boot and
the actuator endpoints. It expects a Zeebe broker at `zeebe.broker.contactpoint`
(`localhost:26500` by default).

## Branches

- `dev` is the active development branch — all PRs should target `dev`.
- `main` holds released versions.

## Contributing

See [contributing.md](contributing.md), our [Code of Conduct](CODE_OF_CONDUCT.md) and the [security policy](security.md).
