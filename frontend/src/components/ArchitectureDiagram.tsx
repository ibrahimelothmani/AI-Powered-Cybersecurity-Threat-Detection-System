import React, { useEffect } from 'react'
import mermaid from 'mermaid'

const ArchitectureDiagram: React.FC = () => {
  useEffect(() => {
    mermaid.initialize({
      startOnLoad: true,
      theme: 'dark',
      sequence: {
        diagramMarginX: 50,
        diagramMarginY: 10,
        actorMargin: 50,
        messageMargin: 35
      },
      flowchart: {
        curve: 'basis',
        padding: 15
      }
    })
  }, [])

  const diagram = `
    graph LR
      subgraph Network
        A[Network Traffic] -->|Collect| B[Data Ingestion]
        L[System Logs] -->|Collect| B
      end

      subgraph Processing
        B -->|Stream| C[Kafka]
        C -->|Process| D[AI Service]
        D -->|Detect| E[Threat Analysis]
      end

      subgraph Frontend
        E -->|Alert| F[Dashboard]
        F -->|Display| G[Threats View]
        F -->|Monitor| H[Analytics View]
      end

      subgraph Monitoring
        I[Prometheus] -->|Metrics| J[Grafana]
        J -->|Visualize| K[Monitoring Dashboard]
      end

      classDef network fill:#ff9999,stroke:#ff0000,stroke-width:2px
      classDef processing fill:#99ff99,stroke:#00ff00,stroke-width:2px
      classDef frontend fill:#9999ff,stroke:#0000ff,stroke-width:2px
      classDef monitoring fill:#ffff99,stroke:#ffff00,stroke-width:2px

      class A,L network
      class B,C,D,E processing
      class F,G,H frontend
      class I,J,K monitoring
  `

  return (
    <div className="mermaid" style={{ background: '#1e293b', padding: '20px', borderRadius: '8px' }}>
      {diagram}
    </div>
  )
}

export default ArchitectureDiagram