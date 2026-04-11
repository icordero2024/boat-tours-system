import { useEffect, useMemo, useState } from 'react'

const TOURS_API = import.meta.env.VITE_TOURS_API_URL || 'http://localhost:8081'
const GUIDES_API = import.meta.env.VITE_GUIDES_API_URL || 'http://localhost:8082'

const initialTourForm = {
  name: '',
  location: '',
  price: ''
}

const initialGuideForm = {
  name: '',
  community: '',
  boatName: ''
}

function App() {
  const [tours, setTours] = useState([])
  const [guides, setGuides] = useState([])
  const [tourForm, setTourForm] = useState(initialTourForm)
  const [guideForm, setGuideForm] = useState(initialGuideForm)
  const [loading, setLoading] = useState(true)
  const [submittingTour, setSubmittingTour] = useState(false)
  const [submittingGuide, setSubmittingGuide] = useState(false)
  const [message, setMessage] = useState('')
  const [error, setError] = useState('')

  const totalTours = useMemo(() => tours.length, [tours])
  const totalGuides = useMemo(() => guides.length, [guides])

  useEffect(() => {
    loadEverything()
  }, [])

  async function loadEverything() {
    setLoading(true)
    setError('')

    try {
      const [toursResponse, guidesResponse] = await Promise.all([
        fetch(`${TOURS_API}/tours`),
        fetch(`${GUIDES_API}/guides`)
      ])

      if (!toursResponse.ok) {
        throw new Error('No se pudieron cargar los tours')
      }

      if (!guidesResponse.ok) {
        throw new Error('No se pudieron cargar los guías locales')
      }

      const toursData = await toursResponse.json()
      const guidesData = await guidesResponse.json()

      setTours(Array.isArray(toursData) ? toursData : [])
      setGuides(Array.isArray(guidesData) ? guidesData : [])
    } catch (fetchError) {
      setError(fetchError.message || 'Ocurrió un error cargando la información')
    } finally {
      setLoading(false)
    }
  }

  async function handleTourSubmit(event) {
    event.preventDefault()
    setSubmittingTour(true)
    setMessage('')
    setError('')

    try {
      const response = await fetch(`${TOURS_API}/tours`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          name: tourForm.name,
          location: tourForm.location,
          price: Number(tourForm.price)
        })
      })

      const data = await response.json()

      if (!response.ok) {
        throw new Error(data.message || 'No se pudo crear el tour')
      }

      setTourForm(initialTourForm)
      setMessage('Tour registrado correctamente')
      await loadEverything()
    } catch (submitError) {
      setError(submitError.message || 'Error al crear el tour')
    } finally {
      setSubmittingTour(false)
    }
  }

  async function handleGuideSubmit(event) {
    event.preventDefault()
    setSubmittingGuide(true)
    setMessage('')
    setError('')

    try {
      const response = await fetch(`${GUIDES_API}/guides`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(guideForm)
      })

      const data = await response.json()

      if (!response.ok) {
        throw new Error(data.message || 'No se pudo registrar el guía')
      }

      setGuideForm(initialGuideForm)
      setMessage('Guía local registrado correctamente')
      await loadEverything()
    } catch (submitError) {
      setError(submitError.message || 'Error al crear el guía')
    } finally {
      setSubmittingGuide(false)
    }
  }

  return (
    <main className="min-h-screen bg-slate-950 text-slate-100">
      <section className="mx-auto max-w-7xl px-6 py-10">
        <div className="mb-8 rounded-3xl border border-cyan-400/20 bg-slate-900/70 p-8 shadow-2xl shadow-cyan-950/30">
          <p className="mb-3 text-sm font-semibold uppercase tracking-[0.3em] text-cyan-300">
            React + Tailwind + Micronaut microservices
          </p>
          <h1 className="text-4xl font-bold text-white md:text-5xl">
            Community Boat Tours
          </h1>
          <p className="mt-4 max-w-3xl text-slate-300">
            Plataforma local para que personas de la comunidad publiquen tours en lancha y los usuarios
            puedan consultarlos desde un frontend simple, con microservicios separados y bases de datos
            independientes.
          </p>

          <div className="mt-8 grid gap-4 md:grid-cols-3">
            <StatCard label="Tours disponibles" value={totalTours} />
            <StatCard label="Guías registrados" value={totalGuides} />
            <StatCard label="Arquitectura" value="2 servicios" />
          </div>
        </div>

        {(message || error) && (
          <div className={`mb-6 rounded-2xl border px-4 py-3 text-sm ${error ? 'border-red-400/30 bg-red-500/10 text-red-200' : 'border-emerald-400/30 bg-emerald-500/10 text-emerald-200'}`}>
            {error || message}
          </div>
        )}

        <div className="grid gap-8 lg:grid-cols-2">
          <Panel title="Registrar tour" subtitle="POST /tours en el microservicio de tours">
            <form className="grid gap-4" onSubmit={handleTourSubmit}>
              <Input
                label="Nombre del tour"
                value={tourForm.name}
                onChange={(event) => setTourForm({ ...tourForm, name: event.target.value })}
                placeholder="Tour al manglar"
              />
              <Input
                label="Ubicación"
                value={tourForm.location}
                onChange={(event) => setTourForm({ ...tourForm, location: event.target.value })}
                placeholder="Paquera"
              />
              <Input
                label="Precio"
                type="number"
                step="0.01"
                min="0"
                value={tourForm.price}
                onChange={(event) => setTourForm({ ...tourForm, price: event.target.value })}
                placeholder="35.50"
              />
              <button
                type="submit"
                disabled={submittingTour}
                className="rounded-2xl bg-cyan-400 px-4 py-3 font-semibold text-slate-950 transition hover:bg-cyan-300 disabled:cursor-not-allowed disabled:opacity-70"
              >
                {submittingTour ? 'Guardando...' : 'Crear tour'}
              </button>
            </form>
          </Panel>

          <Panel title="Registrar guía local" subtitle="POST /guides en el microservicio de guías">
            <form className="grid gap-4" onSubmit={handleGuideSubmit}>
              <Input
                label="Nombre"
                value={guideForm.name}
                onChange={(event) => setGuideForm({ ...guideForm, name: event.target.value })}
                placeholder="Marvin Solís"
              />
              <Input
                label="Comunidad"
                value={guideForm.community}
                onChange={(event) => setGuideForm({ ...guideForm, community: event.target.value })}
                placeholder="Isla Chira"
              />
              <Input
                label="Nombre de la lancha"
                value={guideForm.boatName}
                onChange={(event) => setGuideForm({ ...guideForm, boatName: event.target.value })}
                placeholder="La Sirenita"
              />
              <button
                type="submit"
                disabled={submittingGuide}
                className="rounded-2xl bg-emerald-400 px-4 py-3 font-semibold text-slate-950 transition hover:bg-emerald-300 disabled:cursor-not-allowed disabled:opacity-70"
              >
                {submittingGuide ? 'Guardando...' : 'Registrar guía'}
              </button>
            </form>
          </Panel>
        </div>

        <div className="mt-8 grid gap-8 lg:grid-cols-2">
          <Panel title="Tours disponibles" subtitle="GET /tours">
            {loading ? (
              <p className="text-slate-400">Cargando tours...</p>
            ) : tours.length === 0 ? (
              <EmptyState text="Todavía no hay tours registrados." />
            ) : (
              <div className="grid gap-4">
                {tours.map((tour) => (
                  <article key={tour.id} className="rounded-2xl border border-slate-800 bg-slate-900/80 p-4">
                    <div className="flex items-start justify-between gap-3">
                      <div>
                        <h3 className="text-lg font-semibold text-white">{tour.name}</h3>
                        <p className="mt-1 text-sm text-slate-400">Ubicación: {tour.location}</p>
                      </div>
                      <span className="rounded-full bg-cyan-400/15 px-3 py-1 text-sm font-semibold text-cyan-200">
                        ${Number(tour.price).toFixed(2)}
                      </span>
                    </div>
                  </article>
                ))}
              </div>
            )}
          </Panel>

          <Panel title="Guías locales" subtitle="GET /guides">
            {loading ? (
              <p className="text-slate-400">Cargando guías...</p>
            ) : guides.length === 0 ? (
              <EmptyState text="Todavía no hay guías registrados." />
            ) : (
              <div className="grid gap-4">
                {guides.map((guide) => (
                  <article key={guide.id} className="rounded-2xl border border-slate-800 bg-slate-900/80 p-4">
                    <h3 className="text-lg font-semibold text-white">{guide.name}</h3>
                    <p className="mt-1 text-sm text-slate-400">Comunidad: {guide.community}</p>
                    <p className="mt-1 text-sm text-slate-400">Lancha: {guide.boatName}</p>
                  </article>
                ))}
              </div>
            )}
          </Panel>
        </div>
      </section>
    </main>
  )
}

function StatCard({ label, value }) {
  return (
    <div className="rounded-2xl border border-slate-800 bg-slate-950/70 p-5">
      <p className="text-sm text-slate-400">{label}</p>
      <p className="mt-2 text-2xl font-bold text-white">{value}</p>
    </div>
  )
}

function Panel({ title, subtitle, children }) {
  return (
    <section className="rounded-3xl border border-slate-800 bg-slate-900/70 p-6 shadow-xl shadow-slate-950/20">
      <div className="mb-5">
        <h2 className="text-2xl font-semibold text-white">{title}</h2>
        <p className="mt-1 text-sm text-slate-400">{subtitle}</p>
      </div>
      {children}
    </section>
  )
}

function Input({ label, ...props }) {
  return (
    <label className="grid gap-2 text-sm text-slate-300">
      <span>{label}</span>
      <input
        {...props}
        className="rounded-2xl border border-slate-700 bg-slate-950 px-4 py-3 text-white outline-none transition focus:border-cyan-400"
      />
    </label>
  )
}

function EmptyState({ text }) {
  return (
    <div className="rounded-2xl border border-dashed border-slate-700 bg-slate-950/50 p-6 text-center text-slate-400">
      {text}
    </div>
  )
}

export default App
